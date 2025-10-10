package venomized.mc.mods.swsignals;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomized.mc.mods.swsignals.block.se.SeBlocks;
import venomized.mc.mods.swsignals.block.se.SeModels;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;
import venomized.mc.mods.swsignals.client.ClientEvents;
import venomized.mc.mods.swsignals.client.ForgeClientEvents;
import venomized.mc.mods.swsignals.client.SwMenus;
import venomized.mc.mods.swsignals.data.BlockStateDataGenerator;
import venomized.mc.mods.swsignals.data.RecipeDataGenerator;
import venomized.mc.mods.swsignals.data.SoundEventDataGenerator;
import venomized.mc.mods.swsignals.item.SwItems;
import venomized.mc.mods.swsignals.network.Networking;

@Mod(SwSignal.MOD_ID)
public class SwSignal {
    public static final String MOD_ID = "swsignal";
    public static final Logger LOGGER = LogManager.getLogger(SwSignal.class);

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab> SW_SIGNAL_TAB = REGISTRATE.get()
            .defaultCreativeTab("extended_signals")
            .register();

    private static Networking SW_SIGNAL_NETWORK;

    public SwSignal() {
        initializeContent();

        IEventBus eventbus = REGISTRATE.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        eventbus.register(this);

        AllSounds.SOUNDS.register(eventbus);

        // SW_SIGNAL_TAB..register(eventbus);

        SwMenus.MENUS.register(eventbus);

        EventHandler eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientEvents clientEvents = new ClientEvents();
            ForgeClientEvents forgeClientEvents = new ForgeClientEvents();
            eventbus.register(clientEvents);
            MinecraftForge.EVENT_BUS.register(forgeClientEvents);
        });

        SW_SIGNAL_NETWORK = new Networking();
        Networking.init();
    }

    private static void initializeContent() {
        swedishContent();
    }

    private static void swedishContent() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SeModels.init());

        SeBlocks.init();
        SeBlockEntities.init();

        SwItems.init();
    }

    public static final ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Networking network() {
        return SW_SIGNAL_NETWORK;
    }

    @SubscribeEvent
    public void onDataGenerator(GatherDataEvent e) {
        // e.getGenerator().addProvider(e.includeClient(), new ModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(true, new BlockStateDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        // e.getGenerator().addProvider(e.includeClient(), new ItemModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(true, new SoundEventDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));

        e.getGenerator().addProvider(e.includeServer(), new RecipeDataGenerator(e.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public void onRegisterEvent(RegisterEvent e) {
        // e.register(EdgePointType.SIGNAL
    }

    @SubscribeEvent
    public void onCreativeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == SW_SIGNAL_TAB.getKey()) {
            REGISTRATE.get().getAll(Registries.ITEM).forEach(item -> event.accept(item.get()));
            // REGISTRATE.get().getAll(Registries.BLOCK).forEach(block -> event.accept(new ItemStack(block.get())));
        }
    }
}
