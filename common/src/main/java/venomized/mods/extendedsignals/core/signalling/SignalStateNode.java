package venomized.mods.extendedsignals.core.signalling;

import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import lombok.*;
import lombok.experimental.Accessors;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;

import javax.annotation.Nullable;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@With
@Builder
public class SignalStateNode {
    public static final StreamCodec<FriendlyByteBuf, SignalStateNode> STREAM_CODEC = StreamCodec.of(
            SignalStateNode::encode,
            SignalStateNode::decode
    );

    public static SignalStateNode STOP(TrackEdgePoint trackEdgePoint, boolean front) {
        return new SignalStateNode(trackEdgePoint, front).setProceed(false);
    }

    public static SignalStateNode STOP(UUID trackEdgePoint, boolean front) {
        return new SignalStateNode(trackEdgePoint, front).setProceed(false);
    }

    public static SignalStateNode INVALID(TrackEdgePoint trackEdgePoint, boolean front) {
        return new SignalStateNode(trackEdgePoint, front).setValid(false);
    }

    public static SignalStateNode INVALID(UUID trackEdgePoint, boolean front) {
        return new SignalStateNode(trackEdgePoint, front).setValid(false);
    }

    public SignalStateNode(TrackEdgePoint point, boolean front) {
        this(point.getId(), front);
    }

    public SignalStateNode(UUID point, boolean front) {
        this.thisStateID = point;
        this.thisStateFront = front;
    }

    public static SignalStateNode fromCache(TrackEdgePoint point, boolean front) {
        return fromCache(point.getId(), front);
    }

    public static SignalStateNode fromCache(UUID id, boolean front) {
        return ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY.getSignalState(id, front);
    }

    @Getter
    @Setter
    private boolean thisStateFront;
    @Getter
    @Setter
    private UUID thisStateID;
    @Getter
    @Setter
    public UUID reservedBy;
    @Setter
    @Getter
    private boolean proceed;
    @Setter
    @Getter
    private double maxProceedSpeed = Double.MAX_VALUE;
    @Setter
    @Getter
    private double distanceToNextSignal = -1;
    @Setter
    @Getter
    private boolean valid = true;
    @EqualsAndHashCode.Exclude
    @Setter
    @Getter
    private long lastAspectChangeTick = -1;

    private boolean nextStateFront;
    private UUID nextStateID;
    @Nullable
    public SignalStateNode getNextState() {
        return fromCache(nextStateID, nextStateFront);
    }

    public SignalStateNode setNextState(SignalStateNode nextState) {
        // hasNextState = nextState != null && nextState.isValid();
        if (nextState == null) {
            if (nextStateID != null) {
                SignalStateNode current = fromCache(nextStateID, nextStateFront);
                current.previousStateID = null;
            }
            nextStateID = null;
            return this;
        }
        nextStateFront = nextState.thisStateFront;
        nextStateID = nextState.thisStateID;
        nextState.previousStateFront = this.thisStateFront;
        nextState.previousStateID = this.nextStateID;

        return this;
    }

    private boolean previousStateFront;
    private UUID previousStateID;

    @Nullable
    public SignalStateNode getPreviousState() {
        return fromCache(previousStateID, previousStateFront);
    }

    public SignalStateNode setPreviousState(SignalStateNode previousState) {
        // hasPreviousState = previousState != null && previousState.isValid();
        if (previousState == null) {
            if (previousStateID != null) {
                SignalStateNode current = fromCache(previousStateID, previousStateFront);
                current.nextStateID = null;
            }
            previousStateID = null;
            return this;
        }
        previousStateFront = previousState.thisStateFront;
        previousStateID = previousState.thisStateID;
        return this;
    }

    @Deprecated
    public SignalStateNode setAxisDirection(Direction.AxisDirection direction) {
        nextStateFront = direction == Direction.AxisDirection.POSITIVE;
        return this;
    }

    @Deprecated
    @Nullable
    public Direction.AxisDirection getAxisDirection() {
        return nextStateFront ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE;
    }
    @NotNull
    @Getter
    @Setter
    private Map<String, String> miscTags = new Object2ReferenceArrayMap<>();

    private static SignalStateNode decode(@NotNull FriendlyByteBuf friendlyByteBuf) {
        return SignalStateNode.fromNBT(friendlyByteBuf.readNbt());
    }

    private static void encode(FriendlyByteBuf buf, SignalStateNode state) {
        buf.writeNbt(state.toNBT());
    }

    public static @Nullable SignalStateNode fromNBT(@Nullable final CompoundTag tag) {
        if (tag == null) {
            return null;
        }
        final SignalStateNode signalStateNode = new SignalStateNode();
        if (tag.contains("this_state_id"))
            signalStateNode.thisStateID = tag.getUUID("this_state_id");
        signalStateNode.thisStateFront = tag.getBoolean("this_state_front");

        signalStateNode.setProceed(tag.getBoolean("proceed"));
        signalStateNode.setMaxProceedSpeed(tag.getDouble("proceed_speed"));
        signalStateNode.setDistanceToNextSignal(tag.getDouble("distance_next_signal"));
        signalStateNode.setValid(tag.getBoolean("valid"));
        if (tag.contains("train_reserved"))
            signalStateNode.setReservedBy(tag.getUUID("train_reserved"));

        if (tag.contains("next_state_id"))
            signalStateNode.nextStateID = tag.getUUID("next_state_id");
        signalStateNode.nextStateFront = tag.getBoolean("next_state_front");

        if (tag.contains("previous_state_id"))
            signalStateNode.previousStateID = tag.getUUID("previous_state_id");
        signalStateNode.previousStateFront = tag.getBoolean("previous_state_front");

        if (tag.getBoolean("has_next_state")) {
            signalStateNode.setNextState(SignalStateNode.fromNBT(tag.getCompound("next_state")));
        }

        CompoundTag miscCompTag = tag.getCompound("misc_tags");
        miscCompTag.getAllKeys().forEach(e -> signalStateNode.miscTags.put(e, miscCompTag.getString(e)));
        return signalStateNode;
    }

    public CompoundTag toNBT() {
        final CompoundTag tag = new CompoundTag();
        if (thisStateID != null)
            tag.putUUID("this_state_id", thisStateID);
        tag.putBoolean("this_state_front", thisStateFront);

        tag.putBoolean("proceed", isProceed());
        tag.putDouble("proceed_speed", getMaxProceedSpeed());
        tag.putDouble("distance_next_signal", getDistanceToNextSignal());
        tag.putBoolean("valid", isValid());
        if (getReservedBy() != null)
            tag.putUUID("train_reserved", getReservedBy());

        // tag.putBoolean(TAG_HAS_NEXT_SIGNAL_STATE_NAME, hasNextState);
        if (nextStateID != null)
            tag.putUUID("next_state_id", nextStateID);
        tag.putBoolean("next_state_front", nextStateFront);

        if (previousStateID != null)
            tag.putUUID("previous_state_id", previousStateID);
        tag.putBoolean("previous_state_front", previousStateFront);

        final CompoundTag miscCompTag = new CompoundTag();
        miscTags.entrySet().forEach(entry -> {
            miscCompTag.putString(entry.getKey(), entry.getValue());
        });
        tag.put("misc_tags", miscCompTag);
        return tag;
    }

    public boolean isStop() {
        if (!valid)
            return true;


        // Either if the signal is not aligned to the current signaling state - stop
        // OR
        // If the signal is not displaying a proceed aspect - stop
        return !this.proceed;
    }

    public SignalStateNode copyValues(SignalStateNode other) {
        try {
            for (Field declaredField : this.getClass().getDeclaredFields()) {
                if (declaredField.accessFlags().contains(AccessFlag.STATIC) || declaredField.accessFlags().contains(AccessFlag.FINAL)) {
                    continue;
                }
                declaredField.set(this, declaredField.get(other));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public int networkStateHash() {
        return Objects.hash(
                thisStateID,
                thisStateFront,
                nextStateID,
                nextStateFront,
                previousStateID,
                previousStateFront,
                proceed,
                maxProceedSpeed,
                distanceToNextSignal,
                valid,
                miscTags,
                reservedBy
        );
    }
}
