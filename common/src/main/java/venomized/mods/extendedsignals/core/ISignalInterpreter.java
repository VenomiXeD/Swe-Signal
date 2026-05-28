package venomized.mods.extendedsignals.core;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface ISignalInterpreter<T extends ISignalAspect> {
    @NotNull
    T interpret(SignalStateNode state, Direction.AxisDirection incomingDirection);
}
