package venomized.mods.extendedsignals.de.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

public final class GermanyBlocks {
    // HV System
    // HP + VR
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
    // HP, Hauptsignal
    public static BlockEntry<BlockHVMainSignal> HV_MAIN_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_hp", BlockHVMainSignal::new)
            .lang("[HV] Main Signal")
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
