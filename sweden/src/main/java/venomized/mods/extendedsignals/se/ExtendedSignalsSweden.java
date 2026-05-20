package venomized.mods.extendedsignals.se;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import venomized.mods.extendedsignals.core.ModTemplate;
import venomized.mods.extendedsignals.se.block.ExtendedSignalsSwedenBlocks;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

@Mod(ExtendedSignalsSweden.MOD_ID)
public class ExtendedSignalsSweden extends ModTemplate {
    public static final String MOD_ID = "extended_signals_se";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public ExtendedSignalsSweden(FMLJavaModLoadingContext context) {
        super(context);
    }

    public static ResourceLocation res(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }


    /**
     *
     */
    @Override
    protected void commonInitialization() {
        ExtendedSignalsSwedenBlocks.init();
        ExtendedSignalsSwedenBlockEntities.init();

        ExtendedSignalsSwedenSounds.init();
    }

    /**
     *
     */
    @Override
    protected void clientInitialization() {

    }
}
