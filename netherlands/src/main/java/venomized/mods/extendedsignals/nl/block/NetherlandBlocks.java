package venomized.mods.extendedsignals.nl.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.nl.ExtendedSignalsNetherlands;
import venomized.mods.extendedsignals.nl.blockentity.BlockEntityCrossingSign;

public final class NetherlandBlocks {
    public static BlockEntry<BlockGate> CROSSING_GATE = RegistrateHelper
            .modelledBlock(registrate(), "nl", "crossings", "gate", BlockGate::new)
            .lang("Crossing Gate")
            .register();

    public static BlockEntry<BlockModelled> CROSSING_SIGN = RegistrateHelper
            .modelledBlock(registrate(), "nl", "signs", "crossing_sign", BlockModelled::modelBlockEntity, BlockEntityCrossingSign::new)
            .lang("Crossing Gate")
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsNetherlands.REGISTRATE.get();
    }

    public static void init() {
    }
}
