package venomized.mods.swsignal.core;

import com.simibubi.create.content.trains.schedule.Schedule;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.EventHandler;
import venomized.mc.mods.swsignals.block.se.SeBlocks;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;
import venomized.mc.mods.swsignals.client.SwMenus;
import venomized.mc.mods.swsignals.client.sound.train.TrainSound;
import venomized.mc.mods.swsignals.client.sound.train.TrainSounds;
import venomized.mc.mods.swsignals.create.DoorInstruction;
import venomized.mc.mods.swsignals.data.BlockStateDataGenerator;
import venomized.mc.mods.swsignals.data.RecipeDataGenerator;
import venomized.mc.mods.swsignals.data.SoundEventDataGenerator;
import venomized.mc.mods.swsignals.data.SwSignalLang;
import venomized.mc.mods.swsignals.item.SwItems;
import venomized.mc.mods.swsignals.network.Networking;

@Mod(SwSignalCore.MOD_ID)
public class SwSignalCore {
    public static final String MOD_ID = "swsignal";
    public static final Logger LOGGER = LogManager.getLogger(SwSignalCore.class);

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab> SW_SIGNAL_TAB = REGISTRATE.get()
            .defaultCreativeTab("extended_signals")
            .register();

    private static Networking SW_SIGNAL_NETWORK;

    public SwSignalCore(FMLJavaModLoadingContext context) {
        initializeContent();

        IEventBus eventbus = context.getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        eventbus.register(this);

        AllSounds.SOUNDS.register(eventbus);
        TrainSounds.TRAIN_SOUNDS_REGISTRY.register(eventbus);

        // SW_SIGNAL_TAB..register(eventbus);

        SwMenus.MENUS.register(eventbus);

        EventHandler eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);

        SW_SIGNAL_NETWORK = new Networking();
        Networking.init();
    }

    private static void initializeContent() {
        Schedule.INSTRUCTION_TYPES.add(Pair.of(resource("door"), DoorInstruction::new));


        swedishContent();
    }

    private static void swedishContent() {
        SeBlocks.init();
        SeBlockEntities.init();

        SwItems.init();
    }

    /**
     * Returns a Resource Location for this mod's namespace
     *
     * @param path Path to resource
     * @return Resource object for this mod
     */
    public static final ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Networking network() {
        return SW_SIGNAL_NETWORK;
    }

    @SubscribeEvent
    public void onDataGenerator(GatherDataEvent e) {
        SwSignalLang.languageEntries();

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
    public void onNewRegistryEvent(NewRegistryEvent e) {
        e.create(new RegistryBuilder<TrainSound>()
                .setName(TrainSounds.TRAIN_SOUNDS_RESOURCE_KEY.location())
                .disableSaving()
        );
    }

    @SubscribeEvent
    public void onCreativeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == SW_SIGNAL_TAB.getKey()) {
            REGISTRATE.get().getAll(Registries.ITEM).forEach(item -> event.accept(item.get()));
            // REGISTRATE.get().getAll(Registries.BLOCK).forEach(block -> event.accept(new ItemStack(block.get())));
        }
    }
}
