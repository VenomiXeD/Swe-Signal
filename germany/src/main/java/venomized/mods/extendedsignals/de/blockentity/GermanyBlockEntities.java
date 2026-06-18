package venomized.mods.extendedsignals.de.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;
import venomized.mods.extendedsignals.de.client.blockentityrenderer.RendererZs3CombinedSignal;

public final class GermanyBlockEntities {
    public static BlockEntityEntry<BlockEntityHVMainSignal> MAIN_SIGNAL = RegistrateHelper
            .simpleBlockEntity(
                    registrate(), "hv_main_signal", BlockEntityHVMainSignal::new, GermanyBlocks.MAIN_SIGNAL
            )
            .renderer(() -> RendererSignal::new)
            .register();
    public static BlockEntityEntry<BlockEntityHVDistantSignal> DISTANT_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_distant_signal", BlockEntityHVDistantSignal::new, GermanyBlocks.DISTANT_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();
    public static BlockEntityEntry<BlockEntityHVCombinedSignal> COMBINED_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_combined_signal", BlockEntityHVCombinedSignal::new, GermanyBlocks.COMBINED_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    public static BlockEntityEntry<BlockEntityHVCombinedZs3Signal> ZS3_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_combined_zs3_signal", BlockEntityHVCombinedZs3Signal::new, GermanyBlocks.ZS3_SIGNAL)
            .renderer(() -> RendererZs3CombinedSignal::new)
            .register();

    public static BlockEntityEntry<BlockEntityHVMainBlockSignal> BLOCK_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_block_main_signal", BlockEntityHVMainBlockSignal::new, GermanyBlocks.BLOCK_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
