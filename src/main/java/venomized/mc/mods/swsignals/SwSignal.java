package venomized.mc.mods.swsignals;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomized.mc.mods.swsignals.block.SwBlocks;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.client.ClientEvents;
import venomized.mc.mods.swsignals.data.BlockStateDataGenerator;
import venomized.mc.mods.swsignals.data.ItemModelDataGenerator;
import venomized.mc.mods.swsignals.data.ModelDataGenerator;
import venomized.mc.mods.swsignals.item.SwItems;

@Mod(SwSignal.MOD_ID)
public class SwSignal {
    public static final String MOD_ID = "swsignal";

    public static final Logger LOGGER = LogManager.getLogger(SwSignal.class);

    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SwSignal.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("sw_tab",
            ()->
                CreativeModeTab.builder()
                        .title(Component.literal("Swedish Signals"))
                        .displayItems((parameters,output)->{
                            output.acceptAll(SwItems.ITEMS.getEntries().stream().map(e->new ItemStack(e.get())).toList());
                        })
                        .build()
            );

    public SwSignal() {
        IEventBus eventbus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        eventbus.register(this);
        SwBlocks.BLOCKS.register(eventbus);
        SwItems.ITEMS.register(eventbus);
        SwBlockEntities.BLOCK_ENTITIES.register(eventbus);

        CREATIVE_TABS.register(eventbus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventbus.register(ClientEvents.class);
        });



    }

    @SubscribeEvent
    public void onDataGenerator(GatherDataEvent e) {
        e.getGenerator().addProvider(e.includeClient(), new ModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(true, new BlockStateDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(e.includeClient(), new ItemModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
    }
}
