package venomized.mc.mods.swsignals;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomized.mc.mods.swsignals.block.SwBlocks;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.client.ClientEvents;
import venomized.mc.mods.swsignals.data.BlockStateDataGenerator;
import venomized.mc.mods.swsignals.data.ModelDataGenerator;
import venomized.mc.mods.swsignals.item.SwItems;

@Mod(SwSignal.MOD_ID)
public class SwSignal {
    public static final String MOD_ID = "swsignal";

    public static final Logger LOGGER = LogManager.getLogger(SwSignal.class);

    public SwSignal() {
        IEventBus eventbus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        eventbus.register(this);
        SwBlocks.BLOCKS.register(eventbus);
        SwItems.ITEMS.register(eventbus);
        SwBlockEntities.BLOCK_ENTITIES.register(eventbus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventbus.register(ClientEvents.class);
        });

    }

    @SubscribeEvent
    public void onDataGenerator(GatherDataEvent e) {
        e.getGenerator().addProvider(e.includeClient(), new ModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(true, new BlockStateDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
    }



}
