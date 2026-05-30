package venomized.mods.extendedsignals.de.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

public class GermanyBlocks {


    // HP + VR
    public static final BlockEntry<BlockHVCombinedSignal> COMBINED_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hv_hp_vr", BlockHVCombinedSignal::new)
            .register();
    public static final BlockEntry<BlockHVMainBlockSignal> BLOCK_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hv_bk_hp_s", BlockHVMainBlockSignal::new)
            .register();
    // HP, Hauptsignal
    public static BlockEntry<BlockHVMainSignal> MAIN_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hv_hp", BlockHVMainSignal::new)
            .register();

    // VR, Vorsignal
    public static final BlockEntry<? extends Block> DISTANT_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hv_vr", BlockHVDistantSignal::new)
            .register();

    public static final BlockEntry<BlockZsCombinedSignal> ZS3_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hv_hp_vr_zs3", BlockZsCombinedSignal::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
