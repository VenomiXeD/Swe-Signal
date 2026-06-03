package venomized.mods.extendedsignals.core.signalling;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

public interface ISignalInterpreter<T extends ISignalAspect> {
    @NotNull
    T interpret(SignalStateNode state, Direction.AxisDirection incomingDirection);
}
