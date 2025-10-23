package venomized.mc.mods.swsignals.util;

import org.spongepowered.asm.mixin.Unique;


public interface ITrainDoorData {
    @Unique
    public boolean swe_Signal$doorForcedClosed();
    @Unique
    public boolean swe_Signal$setDoorForcedClosed(boolean closed);
}
