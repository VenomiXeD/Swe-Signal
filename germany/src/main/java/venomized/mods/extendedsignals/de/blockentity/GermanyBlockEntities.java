package venomized.mods.extendedsignals.de.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererCrossingGate;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;
import venomized.mods.extendedsignals.de.blockentity.hv.BlockEntityHVCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.hv.BlockEntityHVDistantSignal;
import venomized.mods.extendedsignals.de.blockentity.hv.BlockEntityHVMainBlockSignal;
import venomized.mods.extendedsignals.de.blockentity.hv.BlockEntityHVMainSignal;
import venomized.mods.extendedsignals.de.blockentity.hvk.*;
import venomized.mods.extendedsignals.de.blockentity.ks.BlockEntityKsCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.ks.BlockEntityKsDistantRepeaterSignal;
import venomized.mods.extendedsignals.de.blockentity.ks.BlockEntityKsDistantSignal;
import venomized.mods.extendedsignals.de.blockentity.ks.BlockEntityKsMainSignal;
import venomized.mods.extendedsignals.de.client.blockentityrenderer.RendererCrossingLight;
import venomized.mods.extendedsignals.de.client.blockentityrenderer.RendererHVk;
import venomized.mods.extendedsignals.de.client.blockentityrenderer.RendererKs;
import venomized.mods.extendedsignals.de.client.blockentityrenderer.RendererZs3CombinedSignal;

public final class GermanyBlockEntities {
    // HV System
    public static final class HVBlockEntities {
        public static BlockEntityEntry<BlockEntityHVMainSignal> HV_MAIN_SIGNAL = RegistrateHelper
                .simpleBlockEntity(
                        registrate(), "hv_main_signal", BlockEntityHVMainSignal::new, GermanyBlocks.HVBlocks.HV_MAIN_SIGNAL
                )
                .renderer(() -> RendererSignal::new)
                .register();
        public static BlockEntityEntry<BlockEntityHVDistantSignal> HV_DISTANT_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hv_distant_signal", BlockEntityHVDistantSignal::new, GermanyBlocks.HVBlocks.HV_DISTANT_SIGNAL)
                .renderer(() -> RendererSignal::new)
                .register();
        public static BlockEntityEntry<BlockEntityHVCombinedSignal> HV_COMBINED_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hv_combined_signal", BlockEntityHVCombinedSignal::new, GermanyBlocks.HVBlocks.HV_COMBINED_SIGNAL)
                .renderer(() -> RendererZs3CombinedSignal::new)
                .register();
        public static BlockEntityEntry<BlockEntityHVMainBlockSignal> HV_BLOCK_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hv_block_main_signal", BlockEntityHVMainBlockSignal::new, GermanyBlocks.HVBlocks.HV_BLOCK_SIGNAL)
                .renderer(() -> RendererSignal::new)
                .register();

        public static void init() {
        }
    }

    public static final class HVKBlockEntities {
        public static BlockEntityEntry<BlockEntityHVKMainSignal> HVK_MAIN_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hvk_main_signal", BlockEntityHVKMainSignal::new, GermanyBlocks.HVKBlocks.HVK_MAIN_SIGNAL)
                .renderer(() -> RendererHVk::new)
                .register();

        public static BlockEntityEntry<BlockEntityHVKDistantSignal> HVK_DISTANT_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hvk_distant_signal", BlockEntityHVKDistantSignal::new, GermanyBlocks.HVKBlocks.HVK_DISTANT_SIGNAL)
                .renderer(() -> RendererHVk::new)
                .register();

        public static BlockEntityEntry<BlockEntityHVKCombinedSignal> HVK_COMBINED_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hvk_combined_signal", BlockEntityHVKCombinedSignal::new, GermanyBlocks.HVKBlocks.HVK_COMBINED_SIGNAL)
                .renderer(() -> RendererHVk::new)
                .register();

        public static BlockEntityEntry<BlockEntityHVKBlockSignal> HVK_BLOCK_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hvk_block_signal", BlockEntityHVKBlockSignal::new, GermanyBlocks.HVKBlocks.HVK_BLOCK_SIGNAL)
                .renderer(() -> RendererHVk::new)
                .register();

        public static BlockEntityEntry<BlockEntityHVKBlockCombinedSignal> HVK_BLOCK_COMBINED_SIGNAL = RegistrateHelper
                .simpleBlockEntity(registrate(), "hvk_block_combined_signal", BlockEntityHVKBlockCombinedSignal::new, GermanyBlocks.HVKBlocks.HVK_BLOCK_COMBINED_SIGNAL)
                .renderer(() -> RendererHVk::new)
                .register();

        public static void init() {
        }
    }

    // KS System
    public static BlockEntityEntry<BlockEntityKsMainSignal> KS_MAIN_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "ks_main_signal", BlockEntityKsMainSignal::new, GermanyBlocks.KSBlocks.KS_MAIN_SIGNAL)
            .renderer(() -> RendererKs::new)
            .register();

    public static BlockEntityEntry<BlockEntityKsDistantRepeaterSignal> KS_DISTANT_REPEATER_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "ks_distant_repeater_signal", BlockEntityKsDistantRepeaterSignal::new, GermanyBlocks.KSBlocks.KS_DISTANT_REPEATER_SIGNAL)
            .renderer(() -> RendererKs::new)
            .register();

    public static BlockEntityEntry<BlockEntityKsDistantSignal> KS_DISTANT_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "ks_distant_signal", BlockEntityKsDistantSignal::new, GermanyBlocks.KSBlocks.KS_DISTANT_SIGNAL)
            .renderer(() -> RendererKs::new)
            .register();


    public static BlockEntityEntry<BlockEntityKsCombinedSignal> KS_COMBINED_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "ks_combined_signal", BlockEntityKsCombinedSignal::new, GermanyBlocks.KSBlocks.KS_COMBINED_SIGNAL)
            .renderer(() -> RendererKs::new)
            .register();

    // Misc
    public static BlockEntityEntry<BlockEntityGate> CROSSING_GATE = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_gate", BlockEntityGate::new, GermanyBlocks.CROSSING_GATE)
            .renderer(() -> RendererCrossingGate::new)
            .register();

    public static BlockEntityEntry<BlockEntityCrossingLight> CROSSING_LIGHT = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_light", BlockEntityCrossingLight::new, GermanyBlocks.CROSSING_LIGHT)
            .renderer(() -> RendererCrossingLight::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
        HVBlockEntities.init();
        HVKBlockEntities.init();
    }
}
