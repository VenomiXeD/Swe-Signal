package venomized.mods.extendedsignals.core;

import com.simibubi.create.content.trains.entity.TravellingPoint;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.util.NBTHelp;

import javax.annotation.Nullable;

@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RawSignalState {
    private static final String TAG_PROCEED_NAME = "proceed";
    private static final String TAG_DIRECTION_NAME = "signal_direction";

    public RawSignalState() {
    }

    private boolean proceed;
    private float distanceToNextSignal = -1;
    private float maxProceedSpeed = -1;

    @Nullable
    private Direction.AxisDirection axisDirection;


    @Nullable
    private TravellingPoint.SteerDirection upcomingJunctionSteerDirection;

    @Nullable
    private RawSignalState nextState;

    public static RawSignalState fromNBT(final CompoundTag tag) {
        final RawSignalState rawSignalState = new RawSignalState();
        rawSignalState.setProceed(tag.getBoolean(TAG_PROCEED_NAME));
        rawSignalState.setAxisDirection(
                tag.contains(TAG_DIRECTION_NAME)
                        ? NBTHelper.readEnum(tag, TAG_DIRECTION_NAME, Direction.AxisDirection.class)
                        : null
        );

        return rawSignalState;
    }

    public CompoundTag toNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putBoolean(TAG_PROCEED_NAME, isProceed());
        NBTHelp.safeWriteEnum(tag, TAG_DIRECTION_NAME, axisDirection);
        return tag;
    }
}
