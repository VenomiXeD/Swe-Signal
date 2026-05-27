import com.simibubi.create.content.trains.entity.TravellingPoint;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import static org.junit.jupiter.api.Assertions.*;

class SignalStateNodeUnitTest {
    @Test
    void toNBTAndFromNBTPreservesBasicValues() {
        SignalStateNode original = new SignalStateNode()
                .setProceed(true)
                .setMaxProceedSpeed(42.5)
                .setDistanceToNextSignal(128.75)
                .setAxisDirection(true)
                .setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.LEFT);

        SignalStateNode deserialized = SignalStateNode.fromNBT(original.toNBT());

        assertEquals(original, deserialized);
    }

    @Test
    void toNBTAndFromNBTPreservesNestedNextState() {
        SignalStateNode nextState = new SignalStateNode()
                .setProceed(false)
                .setMaxProceedSpeed(12.0)
                .setDistanceToNextSignal(64.0)
                .setAxisDirection(false)
                .setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.RIGHT);

        SignalStateNode original = new SignalStateNode()
                .setProceed(true)
                .setMaxProceedSpeed(30.0)
                .setDistanceToNextSignal(100.0)
                .setAxisDirection(true)
                .setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.LEFT)
                .setNextState(nextState);

        SignalStateNode deserialized = SignalStateNode.fromNBT(original.toNBT());

        assertEquals(original, deserialized);
        assertNotNull(deserialized.getNextState());
        assertEquals(nextState, deserialized.getNextState());
    }

    @Test
    void toNBTDoesNotWriteNullableFieldsWhenNull() {
        SignalStateNode state = new SignalStateNode()
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

        SignalStateNode state = SignalStateNode.fromNBT(tag);

        assertTrue(state.isProceed());
        assertEquals(15.5, state.getMaxProceedSpeed());
        assertEquals(80.0, state.getDistanceToNextSignal());
        assertNull(state.getNextState());
        assertNull(state.getAxisDirection());
        assertNull(state.getUpcomingJunctionSteerDirection());
    }
}