package venomized.mods.extendedsignals.nl.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.nl.ExtendedSignalsNetherlands;
import venomized.mods.extendedsignals.nl.block.NetherlandBlocks;
import venomized.mods.extendedsignals.nl.client.renderer.RendererGate;

public final class PackBlockEntities {
    public static Registrate registrate() {
        return ExtendedSignalsPack.REGISTRATE.get();
    }

    public static void init() {
    }
}
