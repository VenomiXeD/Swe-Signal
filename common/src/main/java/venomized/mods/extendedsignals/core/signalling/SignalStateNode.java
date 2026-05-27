package venomized.mods.extendedsignals.core.signalling;

import com.simibubi.create.content.trains.entity.TravellingPoint;
import lombok.*;
import lombok.experimental.Accessors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.LevelAccessor;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import javax.annotation.Nullable;
import java.util.UUID;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@With
@Builder
public class SignalStateNode {
    public static final SignalStateNode INVALID = new SignalStateNode().setValid(false);

    private static final String TAG_RESERVED_NAME = "reserved";

    private static final String TAG_PROCEED_NAME = "proceed";
    private static final String TAG_PROCEED_SPEED_NAME = "proceed_speed";
    private static final String TAG_DISTANCE_NEXT_SIGNAL_NAME = "distance_next_signal";
    private static final String TAG_NEXT_SIGNAL_STATE_NAME = "next_state";
    private static final String TAG_HAS_NEXT_SIGNAL_STATE_NAME = "has_" + TAG_NEXT_SIGNAL_STATE_NAME;
    private static final String TAG_STATE_VALID = "valid";

    private static final String TAG_DIRECTION_NAME = "signal_direction";
    private static final String TAG_UPCOMING_SWITCH_DIRECTION_NAME = "upcoming_switch_direction";

    @Setter
    @Getter
    private boolean reserved;

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
    private Boolean axisDirection;
    @Nullable
    @Setter
    @Getter
    private TravellingPoint.SteerDirection upcomingJunctionSteerDirection;


    public static SignalStateNode fromNBT(final CompoundTag tag) {
        final SignalStateNode signalStateNode = new SignalStateNode();
        signalStateNode.setReserved(tag.getBoolean(TAG_RESERVED_NAME));
        signalStateNode.setProceed(tag.getBoolean(TAG_PROCEED_NAME));
        signalStateNode.setMaxProceedSpeed(tag.getDouble(TAG_PROCEED_SPEED_NAME));
        signalStateNode.setDistanceToNextSignal(tag.getDouble(TAG_DISTANCE_NEXT_SIGNAL_NAME));
        signalStateNode.setValid(tag.getBoolean(TAG_STATE_VALID));

        if (tag.getBoolean(TAG_HAS_NEXT_SIGNAL_STATE_NAME)) {
            signalStateNode.setNextState(SignalStateNode.fromNBT(tag.getCompound(TAG_NEXT_SIGNAL_STATE_NAME)));
        }

        if (tag.contains(TAG_DIRECTION_NAME)) {
            signalStateNode.setAxisDirection(tag.getBoolean(TAG_DIRECTION_NAME));
        }
        signalStateNode.setUpcomingJunctionSteerDirection(NBTHelp.safeReadEnum(tag, TAG_UPCOMING_SWITCH_DIRECTION_NAME, TravellingPoint.SteerDirection.class));

        return signalStateNode;
    }

    public CompoundTag toNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putBoolean(TAG_RESERVED_NAME, isReserved());
        tag.putBoolean(TAG_PROCEED_NAME, isProceed());
        tag.putDouble(TAG_PROCEED_SPEED_NAME, getMaxProceedSpeed());
        tag.putDouble(TAG_DISTANCE_NEXT_SIGNAL_NAME, getDistanceToNextSignal());
        tag.putBoolean(TAG_STATE_VALID, isValid());

        tag.putBoolean(TAG_HAS_NEXT_SIGNAL_STATE_NAME, hasDistant);
        if (nextState != null) {
            tag.put(TAG_NEXT_SIGNAL_STATE_NAME, nextState.toNBT());
        }

        if (axisDirection != null) {
            tag.putBoolean(TAG_DIRECTION_NAME, axisDirection);
        }
        NBTHelp.safeWriteEnum(tag, TAG_UPCOMING_SWITCH_DIRECTION_NAME, upcomingJunctionSteerDirection);
        return tag;
    }

    public boolean isStop() {
        return !this.proceed || this.axisDirection == null;
    }

    public SignalStateNode setNextState(SignalStateNode next) {
        this.nextState = next;
        hasDistant = next != null;
        return this;
    }
}
