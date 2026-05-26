package venomized.mods.extendedsignals.de.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;

public final class GermanyBlockEntities {
    public static BlockEntityEntry<BlockEntityMainSignal> MAIN_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "main_signal", BlockEntityMainSignal::new, GermanyBlocks.MAIN_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();
    public static BlockEntityEntry<BlockEntityDistantSignal> DISTANT_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "distant_signal", BlockEntityDistantSignal::new, GermanyBlocks.DISTANT_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();
    public static BlockEntityEntry<BlockEntityCombinedSignal> COMBINED_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "combined_signal", BlockEntityCombinedSignal::new, GermanyBlocks.COMBINED_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
