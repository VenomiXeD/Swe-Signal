package venomized.mods.extendedsignals.nl.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererCrossingGate;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.nl.ExtendedSignalsNetherlands;
import venomized.mods.extendedsignals.nl.block.NetherlandBlocks;
import venomized.mods.extendedsignals.nl.client.renderer.RendererGate;

public final class NetherlandBlockEntities {
    public static BlockEntityEntry<BlockEntityGate> GATE = RegistrateHelper
            .simpleBlockEntity(
                    registrate(), "gate", BlockEntityGate::new, NetherlandBlocks.CROSSING_GATE
            )
            .renderer(() -> RendererGate::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignalsNetherlands.REGISTRATE.get();
    }

    public static void init() {
    }
}
