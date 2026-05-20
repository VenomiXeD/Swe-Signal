package venomized.mods.extendedsignals.core;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class ModTemplate {
    public ModTemplate(FMLJavaModLoadingContext context) {
        commonInitialization();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::clientInitialization);
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> this::serverInitialization);
    }

    protected void commonInitialization() {
    }

    protected void clientInitialization() {
    }

    protected void serverInitialization() {
    }
}
