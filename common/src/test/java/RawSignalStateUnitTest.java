import com.simibubi.create.content.trains.entity.TravellingPoint;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import venomized.mods.extendedsignals.core.RawSignalState;

public class RawSignalStateUnitTest {
    @Test
    public void test_rawSignalStateSerializeTest() {
        RawSignalState nestedSignalState = new RawSignalState();
        nestedSignalState.setProceed(true);
        nestedSignalState.setMaxProceedSpeed(1);
        nestedSignalState.setAxisDirection(Direction.AxisDirection.POSITIVE);
        nestedSignalState.setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.LEFT);
        nestedSignalState.setDistanceToNextSignal(20);
        RawSignalState rawSignalState = new RawSignalState();
        rawSignalState.setProceed(false);
        rawSignalState.setMaxProceedSpeed(2);
        rawSignalState.setAxisDirection(Direction.AxisDirection.NEGATIVE);
        rawSignalState.setUpcomingJunctionSteerDirection(TravellingPoint.SteerDirection.RIGHT);
        rawSignalState.setNextState(nestedSignalState);

        CompoundTag compoundTag = rawSignalState.toNBT();
        Assertions.assertNotNull(compoundTag);

        RawSignalState deserialized = RawSignalState.fromNBT(compoundTag);

        Assertions.assertEquals(deserialized, rawSignalState);
    }
}
