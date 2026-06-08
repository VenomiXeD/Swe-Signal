package venomized.mods.extendedsignals.core.create;

import org.spongepowered.asm.mixin.Unique;


public interface ITrainDoorData {
    @Unique
    boolean extendedSignals$doorForcedClosed();

    @Unique
    void extendedSignals$setDoorForcedClosed(boolean closed);
}
