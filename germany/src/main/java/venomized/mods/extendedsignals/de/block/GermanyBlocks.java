package venomized.mods.extendedsignals.de.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.blockentity.*;

public final class GermanyBlocks {
    // HV System
    // HP + VR
    public static BlockEntry<BlockHVMainSignal> HV_MAIN_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_hp", BlockHVMainSignal::new)
            .lang("[HV] Main Signal")
            .register();
    public static final BlockEntry<BlockHVCombinedSignal> HV_COMBINED_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_hp_vr", BlockHVCombinedSignal::new)
            .lang("[HV] Combined Signal")
            .register();
    public static final BlockEntry<BlockHVMainBlockSignal> HV_BLOCK_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_bk_hp_s", BlockHVMainBlockSignal::new)
            .lang("[HV] Block Signal")
            .register();
    // VR, Vorsignal
    public static final BlockEntry<? extends Block> HV_DISTANT_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_vr", BlockHVDistantSignal::new)
            .lang("[HV] Distant Signal")
            .register();
    // KS System
    public static final BlockEntry<BlockKsMainSignal> KS_MAIN_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "ks.ks_hp", BlockKsMainSignal::new)
            .lang("[KS] Main Signal")
            .register();
    public static final BlockEntry<BlockKsDistantRepeaterSignal> KS_DISTANT_REPEATER_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "ks.ks_vr_r", BlockKsDistantRepeaterSignal::new)
            .lang("[KS] Distant Repeater Signal")
            .register();
    public static final BlockEntry<BlockKsDistantSignal> KS_DISTANT_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "ks.ks_vr", BlockKsDistantSignal::new)
            .lang("[KS] Distant Signal")
            .register();
    public static final BlockEntry<BlockKsCombinedSignal> KS_COMBINED_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "ks.ks_hp_vr", BlockKsCombinedSignal::new)
            .lang("[KS] Combined Signal")
            .register();

    // MISC
    public static BlockEntry<BlockGate> CROSSING_GATE = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "crossings", "gate", BlockGate::new)
            .lang("Railway Crossing Gate")
            .register();

    public static BlockEntry<BlockCrossingLight> CROSSING_LIGHT = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "crossings", "crossing_light", BlockCrossingLight::new)
            .lang("Railway Crossing Light")
            .register();

    // Signs
    public static BlockEntry<BlockModelled> NE_1 = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_1", BlockModelled::withBlockEntity, ModelBlockEntity::new)
            .lang("Auxiliary Sign [Ne 1]")
            .register();

    public static BlockEntry<BlockModelled> NE_2 = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_2_west", BlockModelled::withBlockEntity, BlockEntityNe2::new)
            .lang("Auxiliary Sign [Ne 2]")
            .register();


    public static BlockEntry<BlockModelled> NE_2_SMALL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_2_small_west", BlockModelled::withBlockEntity, BlockEntityNe2Small::new)
            .lang("Auxiliary Sign [Ne 2, Small]")
            .register();

    public static BlockEntry<BlockModelled> NE_3 = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_3", BlockModelled::withBlockEntity, BlockEntityNe3::new)
            .lang("Auxiliary Sign [Ne 3]")
            .register();

    public static BlockEntry<BlockModelled> NE_4 = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_4_west", BlockModelled::withBlockEntity, ModelBlockEntity::new)
            .lang("Auxiliary Sign [Ne 4]")
            .register();

    public static BlockEntry<BlockModelled> NE_5 = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_5_west", BlockModelled::withBlockEntity, BlockEntityNe5::new)
            .lang("Auxiliary Sign [Ne 5]")
            .register();

    public static BlockEntry<BlockModelled> NE_5_SMALL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signs", "ne_5_small_west", BlockModelled::withBlockEntity, BlockEntityNe5Small::new)
            .lang("Auxiliary Sign [Ne 5, Small]")
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
