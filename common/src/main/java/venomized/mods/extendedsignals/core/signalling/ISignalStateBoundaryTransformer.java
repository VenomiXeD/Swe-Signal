package venomized.mods.extendedsignals.core.signalling;

import net.minecraft.core.Direction;

public interface ISignalStateBoundaryTransformer {
    SignalStateNode transformSignalState(Direction.AxisDirection direction, SignalStateNode state);
}
