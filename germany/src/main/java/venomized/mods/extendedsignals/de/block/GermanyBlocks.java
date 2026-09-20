package venomized.mods.extendedsignals.de.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.block.hvk.*;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;
import venomized.mods.extendedsignals.de.blockentity.signs.*;

public final class GermanyBlocks {
    // HV System
    // HP + VR
    public static final class HVBlocks {
        public static final BlockEntry<BlockHVCombinedSignal> HV_COMBINED_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "hv_hp_vr", BlockHVCombinedSignal::new)
                .lang("[HV] Combined Signal")
                .register();
        public static final BlockEntry<BlockHVMainBlockSignal> HV_BLOCK_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "hv_bk_hp_s", BlockHVMainBlockSignal::new)
                .lang("[HV] Block Signal")
                .register();
        // VR, Vorsignal
        public static final BlockEntry<? extends Block> HV_DISTANT_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "hv_vr", BlockHVDistantSignal::new)
                .lang("[HV] Distant Signal")
                .register();
        public static BlockEntry<BlockHVMainSignal> HV_MAIN_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "hv_hp", BlockHVMainSignal::new)
                .lang("[HV] Main Signal")
                .register();

        public static void init() {
        }
    }

    public static final class HVKBlocks {
        public static BlockEntry<BlockHVKMainSignal> HVK_MAIN_SIGNAL = RegistrateHelper
                .modelledBlock(
                        registrate(), "de", "signals", "hvk.main", BlockHVKMainSignal::new)
                .lang("[HVK] Main Signal")
                .register();

        public static BlockEntry<BlockHVKDistantSignal> HVK_DISTANT_SIGNAL = RegistrateHelper
                .modelledBlock(
                        registrate(), "de", "signals", "hvk.distant", BlockHVKDistantSignal::new)
                .lang("[HVK] Distant Signal")
                .register();

        public static BlockEntry<BlockHVKRepeaterSignal> HVK_REPEATER_SIGNAL = RegistrateHelper
                .modelledBlock(
                        registrate(), "de", "signals", "hvk.repeater", BlockHVKRepeaterSignal::new)
                .lang("[HVK] Repeater Signal")
                .register();

        public static BlockEntry<BlockHVKCombinedSignal> HVK_COMBINED_SIGNAL = RegistrateHelper
                .modelledBlock(
                        registrate(), "de", "signals", "hvk.combined", BlockHVKCombinedSignal::new)
                .lang("[HVK] Combined Signal")
                .register();

        public static BlockEntry<BlockHVKBlockSignal> HVK_BLOCK_SIGNAL = RegistrateHelper
                .modelledBlock(
                        registrate(), "de", "signals", "hvk.block", BlockHVKBlockSignal::new)
                .lang("[HVK] Block Signal")
                .register();

        public static BlockEntry<BlockHVKBlockCombinedSignal> HVK_BLOCK_COMBINED_SIGNAL = RegistrateHelper
                .modelledBlock(
                        registrate(), "de", "signals", "hvk.block_combined", BlockHVKBlockCombinedSignal::new)
                .lang("[HVK] Block Combined Signal")
                .register();

        public static void init() {
        }
    }

    public static final class KSBlocks {
        public static final BlockEntry<BlockKsMainSignal> KS_MAIN_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "ks.ks_hp", BlockKsMainSignal::new)
                .lang("[KS] Main Signal")
                .register();
        public static final BlockEntry<BlockKsDistantRepeaterSignal> KS_DISTANT_REPEATER_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "ks.ks_vr_r", BlockKsDistantRepeaterSignal::new)
                .lang("[KS] Distant Repeater Signal")
                .register();
        public static final BlockEntry<BlockKsDistantSignal> KS_DISTANT_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "ks.ks_vr", BlockKsDistantSignal::new)
                .lang("[KS] Distant Signal")
                .register();
        public static final BlockEntry<BlockKsCombinedSignal> KS_COMBINED_SIGNAL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signals", "ks.ks_hp_vr", BlockKsCombinedSignal::new)
                .lang("[KS] Combined Signal")
                .register();

        public static void init() {
        }
    }

    public static final class SignBlocks {
        public static BlockEntry<BlockModelled> LF_6 = RegistrateHelper
                .quickBlockWithBlockEntityAttached(registrate(), "de", "signs", "lf_6", BlockModelled::blockEntity, () -> GermanyBlockEntities.SignBlockEntities.SIGN_LF6::create)
                .register();

        public static BlockEntry<BlockModelled> LF_7 = RegistrateHelper
                .quickBlockWithBlockEntityAttached(registrate(), "de", "signs", "lf_7", BlockModelled::blockEntity, () -> GermanyBlockEntities.SignBlockEntities.SIGN_LF7::create)
                .register();

        public static BlockEntry<BlockModelled> NE_1 = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_1", BlockModelled::modelBlockEntity, ModelBlockEntity::new)
                .lang("Auxiliary Sign [Ne 1]")
                .register();
        public static BlockEntry<BlockModelled> NE_2 = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_2_west", BlockModelled::modelBlockEntity, BlockEntityNe2::new)
                .lang("Auxiliary Sign [Ne 2]")
                .register();
        public static BlockEntry<BlockModelled> NE_2_SMALL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_2_small_west", BlockModelled::modelBlockEntity, BlockEntityNe2Small::new)
                .lang("Auxiliary Sign [Ne 2, Small]")
                .register();
        public static BlockEntry<BlockModelled> NE_3 = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_3", BlockModelled::modelBlockEntity, BlockEntityNe3::new)
                .lang("Auxiliary Sign [Ne 3]")
                .register();
        public static BlockEntry<BlockModelled> NE_4 = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_4_west", BlockModelled::modelBlockEntity, ModelBlockEntity::new)
                .lang("Auxiliary Sign [Ne 4]")
                .register();
        public static BlockEntry<BlockModelled> NE_5 = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_5_west", BlockModelled::modelBlockEntity, BlockEntityNe5::new)
                .lang("Auxiliary Sign [Ne 5]")
                .register();
        public static BlockEntry<BlockModelled> NE_5_SMALL = RegistrateHelper
                .modelledBlock(registrate(), "de", "signs", "ne_5_small_west", BlockModelled::modelBlockEntity, BlockEntityNe5Small::new)
                .lang("Auxiliary Sign [Ne 5, Small]")
                .register();

        public static void init() {
        }
    }

    // MISC
    public static BlockEntry<BlockGate> CROSSING_GATE = RegistrateHelper
            .modelledBlock(registrate(), "de", "crossings", "gate", BlockGate::new)
            .lang("Railway Crossing Gate")
            .register();

    public static BlockEntry<BlockCrossingLight> CROSSING_LIGHT = RegistrateHelper
            .modelledBlock(registrate(), "de", "crossings", "crossing_light", BlockCrossingLight::new)
            .lang("Railway Crossing Light")
            .register();


    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
        HVKBlocks.init();
        KSBlocks.init();
        HVBlocks.init();
        SignBlocks.init();
    }
}
