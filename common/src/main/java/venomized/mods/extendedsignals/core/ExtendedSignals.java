package venomized.mods.extendedsignals.core;

import com.simibubi.create.content.trains.schedule.Schedule;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createmod.catnip.data.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomized.mods.extendedsignals.core.block.CoreBlocks;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.client.ClientSignalNetworkCache;
import venomized.mods.extendedsignals.core.client.KeyMappings;
import venomized.mods.extendedsignals.core.create.DoorInstruction;
import venomized.mods.extendedsignals.core.create.tracks.points.CoreEdgePoints;
import venomized.mods.extendedsignals.core.data.ExtendedSignalsLang;
import venomized.mods.extendedsignals.core.item.ExtendedSignalsItems;
import venomized.mods.extendedsignals.core.menu.CoreMenus;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.util.Debug;

@Mod(ExtendedSignals.MOD_ID)
public class ExtendedSignals {
    public static final String MOD_ID = "extended_signals";
    public static final Logger LOGGER = LogManager.getLogger(ExtendedSignals.class);

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab, ?> CREATIVE_TAB = REGISTRATE.get()
            .defaultCreativeTab("extended_signals")
            .register();

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final ExtendedSignalsNetworking EXTENDED_SIGNAL_NET = new ExtendedSignalsNetworking();
    public static ISignalNetwork EXTENDED_SIGNAL_CACHE_PROXY;
    @SuppressWarnings("InstantiationOfUtilityClass")
    static ServerSignalNetworkCache EXTENDED_SIGNAL_SERVER_CACHE;
    static ClientSignalNetworkCache EXTENDED_SIGNAL_CLIENT_CACHE;

    public ExtendedSignals(IEventBus eventBus, ModContainer modContainer) {
        modContainer.registerConfig(
                ModConfig.Type.SERVER, ExtendedSignalsConfig.SERVER_SPEC
        );

        modContainer.registerConfig(
                ModConfig.Type.CLIENT, ExtendedSignalsConfig.CLIENT_SPEC
        );

        eventBus.register(ExtendedSignals.class);
        NeoForge.EVENT_BUS.register(Events.class);
        eventBus.register(ExtendedSignalsNetworking.class);
        NeoForge.EVENT_BUS.register(Debug.class);

        ExtendedSignalsItems.init();
        CoreBlocks.init();

        CoreBlockEntities.init();
        CoreEdgePoints.init();
        CoreMenus.register(eventBus);


        Schedule.INSTRUCTION_TYPES.add(Pair.of(res("door"), DoorInstruction::new));


    }

    public static ExtendedSignalsNetworking network() {
        return EXTENDED_SIGNAL_NET;
    }

    public static ServerSignalNetworkCache serverNetworkCache() {
        return EXTENDED_SIGNAL_SERVER_CACHE;
    }

    public static ClientSignalNetworkCache clientNetworkCache() {
        return EXTENDED_SIGNAL_CLIENT_CACHE;
    }

    public static ISignalNetwork sidedNetwork(LevelAccessor world) {
        return world.isClientSide() ? clientNetworkCache() : serverNetworkCache();
    }

    /**
     * Returns a Resource Location for this mod's namespace
     *
     * @param path Path to resource
     * @return Resource object for this mod
     */
    public static ResourceLocation res(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void onDataGenerator(net.neoforged.neoforge.data.event.GatherDataEvent e) {
        ExtendedSignalsLang.languageEntries();

        // e.getGenerator().addProvider(e.includeClient(), new ModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        // e.getGenerator().addProvider(true, new BlockStateDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        // e.getGenerator().addProvider(e.includeClient(), new ItemModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        // e.getGenerator().addProvider(true, new SoundEventDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));

        // e.getGenerator().addProvider(e.includeServer(), new RecipeDataGenerator(e.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public static void onNewRegistryEvent(NewRegistryEvent e) {
        // e.create(new RegistryBuilder<TrainSound>()
        //         .setName(TrainSounds.TRAIN_SOUNDS_RESOURCE_KEY.location())
        //         .disableSaving()
        // );
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent e) {
        e.register(KeyMappings.REQUEST_SHUNT);
    }
}
