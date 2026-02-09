package venomized.mc.mods.swsignals.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import venomized.mc.mods.swsignals.block.se.SeModels;
import venomized.mc.mods.swsignals.core.SwSignal;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SwSignal.MOD_ID, value = Dist.CLIENT)
public class SwSignalClient {
    public void onClientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            SeModels.init();
        });
    }
}
