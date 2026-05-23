import com.simibubi.create.content.trains.entity.TravellingPoint;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import static org.junit.jupiter.api.Assertions.*;

class RawSignalStateUnitTest {
    @Test
    void toNBTAndFromNBTPreservesBasicValues() {
        RawSignalState original = new RawSignalState()
                .setProceed(true)
                .setMaxProceedSpeed(42.5)
                .setDistanceToNextSignal(128.75)
                .setAxisDirection(Direction.AxisDirection.POSITIVE)
                .setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.LEFT);

        RawSignalState deserialized = RawSignalState.fromNBT(original.toNBT());

        assertEquals(original, deserialized);
    }

    @Test
    void toNBTAndFromNBTPreservesNestedNextState() {
        RawSignalState nextState = new RawSignalState()
                .setProceed(false)
                .setMaxProceedSpeed(12.0)
                .setDistanceToNextSignal(64.0)
                .setAxisDirection(Direction.AxisDirection.NEGATIVE)
                .setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.RIGHT);

        RawSignalState original = new RawSignalState()
                .setProceed(true)
                .setMaxProceedSpeed(30.0)
                .setDistanceToNextSignal(100.0)
                .setAxisDirection(Direction.AxisDirection.POSITIVE)
                .setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.LEFT)
                .setNextState(nextState);

        RawSignalState deserialized = RawSignalState.fromNBT(original.toNBT());

        assertEquals(original, deserialized);
        assertNotNull(deserialized.getNextState());
        assertEquals(nextState, deserialized.getNextState());
    }

    @Test
    void toNBTDoesNotWriteNullableFieldsWhenNull() {
        RawSignalState state = new RawSignalState()
                .setProceed(true)
                .setMaxProceedSpeed(20.0)
                .setDistanceToNextSignal(50.0)
                .setAxisDirection(null)
                .setUpcomingJunctionSteerDirection(null)
                .setNextState(null);

        CompoundTag tag = state.toNBT();

        assertFalse(tag.contains("next_state"));
        assertFalse(tag.contains("signal_direction"));
        assertFalse(tag.contains("upcoming_switch_direction"));
    }

    @Test
    void fromNBTUsesDefaultsWhenOptionalFieldsAreMissing() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("proceed", true);
        tag.putDouble("proceed_speed", 15.5);
        tag.putDouble("distance_next_signal", 80.0);

        RawSignalState state = RawSignalState.fromNBT(tag);

        assertTrue(state.isProceed());
        assertEquals(15.5, state.getMaxProceedSpeed());
        assertEquals(80.0, state.getDistanceToNextSignal());
        assertNull(state.getNextState());
        assertNull(state.getAxisDirection());
        assertNull(state.getUpcomingJunctionSteerDirection());
    }
}