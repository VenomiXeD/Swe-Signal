package venomized.mods.extendedsignals.de.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

public class GermanyBlocks {

    // VR, Vorsignal
    public static final BlockEntry<? extends Block> DISTANT_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "vr", BlockDistantSignal::new)
            .register();
    // HP + VR
    public static final BlockEntry<BlockCombinedSignal> COMBINED_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hp_vr", BlockCombinedSignal::new)
            .register();
    // HP, Hauptsignal
    public static BlockEntry<BlockMainSignal> MAIN_SIGNAL = RegistrateHelper
            .genericCustomSignalBlock(registrate(), "signals", "de", "hp", BlockMainSignal::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsGermany.REGISTRATE.get();
    }

    public static void init() {
    }
}
