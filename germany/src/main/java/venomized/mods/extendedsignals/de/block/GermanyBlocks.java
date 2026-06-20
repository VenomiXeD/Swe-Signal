package venomized.mods.extendedsignals.de.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

public class GermanyBlocks {


    // HP + VR
    public static final BlockEntry<BlockHVCombinedSignal> COMBINED_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_hp_vr", BlockHVCombinedSignal::new)
            .lang("[HV] Combined Block and Distant Signal")
            .register();
    public static final BlockEntry<BlockHVMainBlockSignal> BLOCK_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_bk_hp_s", BlockHVMainBlockSignal::new)
            .lang("[HV] Block Signal")
            .register();
    // HP, Hauptsignal
    public static BlockEntry<BlockHVMainSignal> MAIN_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_hp", BlockHVMainSignal::new)
            .lang("[HV] Main Signal")
            .register();

    // VR, Vorsignal
    public static final BlockEntry<? extends Block> DISTANT_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_vr", BlockHVDistantSignal::new)
            .lang("[HV] Distant Signal")
            .register();

    public static final BlockEntry<BlockZsCombinedSignal> ZS3_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "de", "signals", "hv_hp_vr_zs3", BlockZsCombinedSignal::new)
            .lang("[HV] Combined block and Distant Signal with Zs3 and Zs3v indicator")
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
