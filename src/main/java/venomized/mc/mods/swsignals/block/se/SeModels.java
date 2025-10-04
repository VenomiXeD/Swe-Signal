package venomized.mc.mods.swsignals.block.se;

import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;

@OnlyIn(Dist.CLIENT)
public class SeModels {

    public static final PartialModel ARM_4 = PartialModel.of(SwSignal.modLoc("block/signals/se/crossing/gate_4"));

    public static void init() {
    }
}
