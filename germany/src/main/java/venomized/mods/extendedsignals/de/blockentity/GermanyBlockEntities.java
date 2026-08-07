package venomized.mods.extendedsignals.de.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;
import venomized.mods.extendedsignals.de.client.blockentityrenderer.RendererZs3CombinedSignal;

public final class GermanyBlockEntities {
    // HV System
    public static BlockEntityEntry<BlockEntityHVMainSignal> HV_MAIN_SIGNAL = RegistrateHelper
            .simpleBlockEntity(
                    registrate(), "hv_main_signal", BlockEntityHVMainSignal::new, GermanyBlocks.HV_MAIN_SIGNAL
            )
            .renderer(() -> RendererSignal::new)
            .register();

    public static BlockEntityEntry<BlockEntityHVDistantSignal> HV_DISTANT_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_distant_signal", BlockEntityHVDistantSignal::new, GermanyBlocks.HV_DISTANT_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    public static BlockEntityEntry<BlockEntityHVCombinedSignal> HV_COMBINED_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_combined_signal", BlockEntityHVCombinedSignal::new, GermanyBlocks.HV_COMBINED_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    public static BlockEntityEntry<BlockEntityHVCombinedZs3Signal> HV_ZS3_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_combined_zs3_signal", BlockEntityHVCombinedZs3Signal::new, GermanyBlocks.HV_COMBINED_ZS3_SIGNAL)
            .renderer(() -> RendererZs3CombinedSignal::new)
            .register();

    public static BlockEntityEntry<BlockEntityHVMainBlockSignal> HV_BLOCK_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "hv_block_main_signal", BlockEntityHVMainBlockSignal::new, GermanyBlocks.HV_BLOCK_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    // KS System
    public static BlockEntityEntry<BlockEntityKsDistantSignal> KS_DISTANT_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "ks_distant_signal", BlockEntityKsDistantSignal::new, GermanyBlocks.KS_DISTANT_SIGNAL)
            .renderer(() -> RendererSignal::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
