package venomized.mc.mods.swsignals.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import venomized.mc.mods.swsignals.core.SwSignal;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = SwSignal.MOD_ID, value = Dist.CLIENT)
public class SwSignalClient {
    @SubscribeEvent
    public static void onClientInit(FMLClientSetupEvent event) {
    }
}
