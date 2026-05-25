package venomized.mods.extendedsignals.core;

import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

public interface ISignalInterpreter<T extends ISignalAspect> {
    T interpret(BlockEntitySignal<?> signalBlockEntity);
}
