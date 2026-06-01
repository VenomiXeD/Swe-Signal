package venomized.mods.extendedsignals.core;

import com.simibubi.create.content.trains.schedule.Schedule;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.createmod.catnip.data.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import venomized.mods.extendedsignals.core.block.CoreBlocks;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.client.ClientSignalNetworkCache;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.core.client.KeyMappings;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSounds;
import venomized.mods.extendedsignals.core.create.DoorInstruction;
import venomized.mods.extendedsignals.core.create.tracks.CoreEdgePoints;
import venomized.mods.extendedsignals.core.data.BlockStateDataGenerator;
import venomized.mods.extendedsignals.core.data.ExtendedSignalsLang;
import venomized.mods.extendedsignals.core.data.RecipeDataGenerator;
import venomized.mods.extendedsignals.core.data.SoundEventDataGenerator;
import venomized.mods.extendedsignals.core.item.ExtendedSignalsItems;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;

@Mod(ExtendedSignalsCore.MOD_ID)
public class ExtendedSignalsCore extends ModTemplate {
    public static final String MOD_ID = "extended_signals";
    public static final Logger LOGGER = LogManager.getLogger(ExtendedSignalsCore.class);

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab> CREATIVE_TAB = REGISTRATE.get()
            .defaultCreativeTab("extended_signals")
            .register();

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final ExtendedSignalsNetworking EXTENDED_SIGNAL_NET = new ExtendedSignalsNetworking();
    @SuppressWarnings("InstantiationOfUtilityClass")
    static ServerSignalNetworkCache EXTENDED_SIGNAL_SERVER_CACHE;
    static ClientSignalNetworkCache EXTENDED_SIGNAL_CLIENT_CACHE;
    public static ISignalNetwork EXTENDED_SIGNAL_CACHE_PROXY;

    public ExtendedSignalsCore(FMLJavaModLoadingContext context) {
        super(context);

        context.getModEventBus().register(ExtendedSignalsCore.class);

        ExtendedSignalsNetworking.init();
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
    public static final ResourceLocation res(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void onDataGenerator(GatherDataEvent e) {
        ExtendedSignalsLang.languageEntries();

        // e.getGenerator().addProvider(e.includeClient(), new ModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(true, new BlockStateDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        // e.getGenerator().addProvider(e.includeClient(), new ItemModelDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        // e.getGenerator().addProvider(true, new SoundEventDataGenerator(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));

        e.getGenerator().addProvider(e.includeServer(), new RecipeDataGenerator(e.getGenerator().getPackOutput()));
    }

    @SubscribeEvent
    public static void onNewRegistryEvent(NewRegistryEvent e) {
        e.create(new RegistryBuilder<TrainSound>()
                .setName(TrainSounds.TRAIN_SOUNDS_RESOURCE_KEY.location())
                .disableSaving()
        );
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent e) {
        e.register(KeyMappings.REQUEST_SHUNT);
    }

    /**
     * @return
     */
    @Override
    protected RegistryEntry<CreativeModeTab> TAB_ENTRY() {
        return CREATIVE_TAB;
    }

    @Override
    protected Registrate REGISTRATE() {
        return REGISTRATE.get();
    }

    @Override
    protected void commonInitialization() {
        ExtendedSignalsItems.init();

        CoreBlocks.init();
        CoreBlockEntities.init();

        CoreEdgePoints.init();

        Schedule.INSTRUCTION_TYPES.add(Pair.of(res("door"), DoorInstruction::new));
    }

    @Override
    protected void clientInitialization() {
        EXTENDED_SIGNAL_CLIENT_CACHE = new ClientSignalNetworkCache();
        EXTENDED_SIGNAL_CACHE_PROXY = EXTENDED_SIGNAL_CLIENT_CACHE;

        ExtendedSignalsCoreModels.init();
    }
}
