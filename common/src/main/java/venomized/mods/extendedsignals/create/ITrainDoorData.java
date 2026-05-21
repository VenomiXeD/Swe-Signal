package venomized.mods.extendedsignals.create;

import org.spongepowered.asm.mixin.Unique;


public interface ITrainDoorData {
    @Unique
    boolean swe_Signal$doorForcedClosed();

    @Unique
    boolean swe_Signal$setDoorForcedClosed(boolean closed);
}
