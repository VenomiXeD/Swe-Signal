package venomized.mods.extendedsignals.core.signalling;

import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import lombok.*;
import lombok.experimental.Accessors;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import javax.annotation.Nullable;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@With
@Builder
public class SignalStateNode {
    private static final int MAX_SIGNAL_RECURSION_STATE_DEPTH = 20;

    public static final SignalStateNode INVALID = new SignalStateNode().setValid(false);
    public static final SignalStateNode STOP = new SignalStateNode();

    private static final String TAG_RESERVED_NAME = "reserved";

    private static final String TAG_PROCEED_NAME = "proceed";
    private static final String TAG_PROCEED_SPEED_NAME = "proceed_speed";
    private static final String TAG_DISTANCE_NEXT_SIGNAL_NAME = "distance_next_signal";
    private static final String TAG_NEXT_SIGNAL_STATE_NAME = "next_state";
    private static final String TAG_HAS_NEXT_SIGNAL_STATE_NAME = "has_" + TAG_NEXT_SIGNAL_STATE_NAME;
    private static final String TAG_STATE_VALID = "valid";

    private static final String TAG_DIRECTION_NAME = "signal_direction";
    private static final String TAG_UPCOMING_SWITCH_DIRECTION_NAME = "upcoming_switch_direction";

    private static final String TAG_CREATE_STATE_NAME = "create_state";

    @Getter
    @Setter
    public boolean reserved;
    @Setter
    @Getter
    private boolean proceed;
    @Setter
    @Getter
    private double maxProceedSpeed = 200; // TODO: Dummy value
    @Setter
    @Getter
    private double distanceToNextSignal = -1;
    @Setter
    @Getter
    private boolean valid = true;

    private Couple<SignalBlockEntity.SignalState> createSignalState = Couple.create(() -> SignalBlockEntity.SignalState.INVALID);

    @EqualsAndHashCode.Exclude
    @Setter
    @Getter
    private long lastAspectChangeTick = -1;

    @Getter
    private boolean hasDistant;
    @Nullable
    @Getter
    private SignalStateNode nextState;

    @Nullable
    @Setter
    @Getter
    private Direction.AxisDirection axisDirection;
    @Nullable
    @Setter
    @Getter
    private TravellingPoint.SteerDirection upcomingJunctionSteerDirection;


    public static SignalStateNode fromNBT(final CompoundTag tag) {
        final SignalStateNode signalStateNode = new SignalStateNode();
        signalStateNode.setProceed(tag.getBoolean(TAG_PROCEED_NAME));
        signalStateNode.setMaxProceedSpeed(tag.getDouble(TAG_PROCEED_SPEED_NAME));
        signalStateNode.setDistanceToNextSignal(tag.getDouble(TAG_DISTANCE_NEXT_SIGNAL_NAME));
        signalStateNode.setValid(tag.getBoolean(TAG_STATE_VALID));

        if (tag.getBoolean(TAG_HAS_NEXT_SIGNAL_STATE_NAME)) {
            signalStateNode.setNextState(SignalStateNode.fromNBT(tag.getCompound(TAG_NEXT_SIGNAL_STATE_NAME)));
        }

        for (boolean side : Iterate.trueAndFalse)
            signalStateNode.setCreateSignalState(side,
                    NBTHelper.readEnum(
                            tag,
                            "%s%d".formatted(TAG_CREATE_STATE_NAME, side ? 0 : 1),
                            SignalBlockEntity.SignalState.class
                    )
            );

        signalStateNode.setAxisDirection(NBTHelp.safeReadEnum(tag, TAG_DIRECTION_NAME, Direction.AxisDirection.class));
        signalStateNode.setUpcomingJunctionSteerDirection(NBTHelp.safeReadEnum(tag, TAG_UPCOMING_SWITCH_DIRECTION_NAME, TravellingPoint.SteerDirection.class));

        return signalStateNode;
    }

    public CompoundTag toNBT() {
        return toNBT(0);
    }

    private CompoundTag toNBT(final int currentRecursionDepth) {
        if (currentRecursionDepth > MAX_SIGNAL_RECURSION_STATE_DEPTH)
            return null;

        final CompoundTag tag = new CompoundTag();
        tag.putBoolean(TAG_PROCEED_NAME, isProceed());
        tag.putDouble(TAG_PROCEED_SPEED_NAME, getMaxProceedSpeed());
        tag.putDouble(TAG_DISTANCE_NEXT_SIGNAL_NAME, getDistanceToNextSignal());
        tag.putBoolean(TAG_STATE_VALID, isValid());

        tag.putBoolean(TAG_HAS_NEXT_SIGNAL_STATE_NAME, hasDistant);
        if (nextState != null) {
            CompoundTag nestedNextSignalState = nextState.toNBT(currentRecursionDepth + 1);
            if (nestedNextSignalState != null)
                tag.put(TAG_NEXT_SIGNAL_STATE_NAME, nestedNextSignalState);
        }

        for (boolean side : Iterate.trueAndFalse)
            NBTHelper.writeEnum(
                    tag,
                    "%s%d".formatted(TAG_CREATE_STATE_NAME, side ? 0 : 1),
                    getCreateSignalState(side)
            );

        NBTHelp.safeWriteEnum(tag, TAG_DIRECTION_NAME, axisDirection);
        NBTHelp.safeWriteEnum(tag, TAG_UPCOMING_SWITCH_DIRECTION_NAME, upcomingJunctionSteerDirection);
        return tag;
    }

    public SignalBlockEntity.SignalState getCreateSignalState(Direction.AxisDirection direction) {
        return getCreateSignalState(direction == Direction.AxisDirection.POSITIVE);
    }

    public SignalBlockEntity.SignalState getCreateSignalState(boolean side) {
        return this.createSignalState.get(side);
    }

    public SignalStateNode setCreateSignalState(boolean side, SignalBlockEntity.SignalState createSignalState) {
        this.createSignalState.set(side, createSignalState);
        return this;
    }

    public boolean isStop(Direction.AxisDirection signalDirection) {
        if (!valid)
            return true;

        if (axisDirection == null)
            return true;

        // Either if the signal is not aligned to the current signaling state - stop
        // OR
        // If the signal is not displaying a proceed aspect - stop
        return signalDirection != axisDirection || !this.proceed;
    }

    public SignalStateNode setNextState(SignalStateNode next) {
        this.nextState = next;
        hasDistant = next != null;
        return this;
    }
}
