package venomized.mods.extendedsignals.core.signalling;

import com.simibubi.create.content.trains.entity.TravellingPoint;
import lombok.*;
import lombok.experimental.Accessors;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import javax.annotation.Nullable;

@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@With
public class RawSignalState {
    public static final RawSignalState INVALID = new RawSignalState().setValid(false);

    private static final String TAG_RESERVED_NAME = "reserved";

    private static final String TAG_PROCEED_NAME = "proceed";
    private static final String TAG_PROCEED_SPEED_NAME = "proceed_speed";
    private static final String TAG_DISTANCE_NEXT_SIGNAL_NAME = "distance_next_signal";
    private static final String TAG_NEXT_SIGNAL_STATE_NAME = "next_state";
    private static final String TAG_STATE_VALID = "valid";

    private static final String TAG_DIRECTION_NAME = "signal_direction";
    private static final String TAG_UPCOMING_SWITCH_DIRECTION_NAME = "upcoming_switch_direction";

    private boolean reserved;

    private boolean proceed;
    private double maxProceedSpeed = 200; // TODO: Dummy value
    private double distanceToNextSignal = -1;
    private boolean valid = true;

    @EqualsAndHashCode.Exclude
    private long lastAspectChangeTick = -1;


    @Nullable
    private RawSignalState nextState;

    @Nullable
    private Direction.AxisDirection axisDirection;
    @Nullable
    private TravellingPoint.SteerDirection upcomingJunctionSteerDirection;


    public static RawSignalState fromNBT(final CompoundTag tag) {
        final RawSignalState rawSignalState = new RawSignalState();
        rawSignalState.setReserved(tag.getBoolean(TAG_RESERVED_NAME));
        rawSignalState.setProceed(tag.getBoolean(TAG_PROCEED_NAME));
        rawSignalState.setMaxProceedSpeed(tag.getDouble(TAG_PROCEED_SPEED_NAME));
        rawSignalState.setDistanceToNextSignal(tag.getDouble(TAG_DISTANCE_NEXT_SIGNAL_NAME));
        rawSignalState.setValid(tag.getBoolean(TAG_STATE_VALID));
        if (tag.contains(TAG_NEXT_SIGNAL_STATE_NAME))
            rawSignalState.setNextState(RawSignalState.fromNBT(tag.getCompound(TAG_NEXT_SIGNAL_STATE_NAME)));

        rawSignalState.setAxisDirection(NBTHelp.safeReadEnum(tag, TAG_DIRECTION_NAME, Direction.AxisDirection.class));
        rawSignalState.setUpcomingJunctionSteerDirection(NBTHelp.safeReadEnum(tag, TAG_UPCOMING_SWITCH_DIRECTION_NAME, TravellingPoint.SteerDirection.class));

        return rawSignalState;
    }

    public CompoundTag toNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putBoolean(TAG_RESERVED_NAME, isReserved());
        tag.putBoolean(TAG_PROCEED_NAME, isProceed());
        tag.putDouble(TAG_PROCEED_SPEED_NAME, getMaxProceedSpeed());
        tag.putDouble(TAG_DISTANCE_NEXT_SIGNAL_NAME, getDistanceToNextSignal());
        tag.putBoolean(TAG_STATE_VALID, isValid());

        if (this.nextState != null)
            tag.put(TAG_NEXT_SIGNAL_STATE_NAME, this.nextState.toNBT());

        NBTHelp.safeWriteEnum(tag, TAG_DIRECTION_NAME, axisDirection);
        NBTHelp.safeWriteEnum(tag, TAG_UPCOMING_SWITCH_DIRECTION_NAME, upcomingJunctionSteerDirection);
        return tag;
    }
}
