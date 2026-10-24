package venomized.mods.extendedsignals.nl.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.nl.ExtendedSignalsNetherlands;

@OnlyIn(Dist.CLIENT)
public final class NetherlandModels {
    public static PartialModel GATE_ARM = PartialModel.of(
            ExtendedSignalsNetherlands.res("block/crossings/nl/gate_arm")
    );

    public static PartialModel CROSSING_SIGN_LET_OP_TREIN = PartialModel.of(
            ExtendedSignalsNetherlands.res("block/signs/nl/crossing_sign_let_op_trein")
    );

    public static void init() {
    }
}
