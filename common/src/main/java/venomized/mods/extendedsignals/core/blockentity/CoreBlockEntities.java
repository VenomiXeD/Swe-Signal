package venomized.mods.extendedsignals.core.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.block.SignalCoreBlocks;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityRepeaterCreateSignal;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntitySignalSpeedModifier;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererATCController;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;

public final class CoreBlockEntities {
    public static final BlockEntityEntry<BlockEntityRailroadCrossingController> BE_SE_RAILROAD_CROSSING_CONTROLLER =
            RegistrateHelper.simpleBlockEntity(
                    registrate(),
                    "be_se_crossing_controller",
                    BlockEntityRailroadCrossingController::new,
                    SignalCoreBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER
            ).register();
    public static final BlockEntityEntry<BlockEntityATCController> BE_ATC_CONTROLLER =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "be_se_atc_controller", BlockEntityATCController::new,
                            SignalCoreBlocks.BLOCK_ATC_CONTROLLER
                    )
                    .renderer(() -> RendererATCController::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityRepeaterCreateSignal> SIGNAL_REPEATER =
            RegistrateHelper.simpleBlockEntity(registrate(),
                    "repeater_signal", BlockEntityRepeaterCreateSignal::new, SignalCoreBlocks.REPEATER_SIGNAL
            ).register();
    public static final BlockEntityEntry<BlockEntitySignalSpeedModifier> MODIFIER_SPEED =
            RegistrateHelper.simpleBlockEntity(registrate(),
                    "modifier_speed", BlockEntitySignalSpeedModifier::new, SignalCoreBlocks.SPEED_MODIFIER
            ).register();
    public static BlockEntityEntry<BlockEntityTrainConfig> TRAIN_CONFIG = RegistrateHelper.simpleBlockEntity(
            registrate(),
            "be_trainconfig",
            BlockEntityTrainConfig::new,
            SignalCoreBlocks.BLOCK_TRAIN_CONFIG
    ).register();

    private static Registrate registrate() {
        return ExtendedSignalsCore.REGISTRATE.get();
    }

    public static void validMainSignalBlock(NonNullSupplier<? extends Block> blockSupplier) {
        // MAIN_SIGNAL_BUILDER.validBlock(blockSupplier);
    }

    public static void validCombinedSignalBlock(NonNullSupplier<? extends Block> blockSupplier) {
        // COMBINED_SIGNAL_BUILDER.validBlock(blockSupplier);
    }

    // private static final BlockEntityBuilder<BlockEntityMainSignal, ?> MAIN_SIGNAL_BUILDER = registrate()
    //         .blockEntity("main_signal", BlockEntityMainSignal::new)
    //         .renderer(() -> RendererSignal::new);
    // public static BlockEntityEntry<BlockEntityMainSignal> MAIN_SIGNAL =
    //         MAIN_SIGNAL_BUILDER.register();

    // private static final BlockEntityBuilder<BlockEntityCombinedSignal, ?> COMBINED_SIGNAL_BUILDER = registrate()
    //         .blockEntity("combined_signal", BlockEntityCombinedSignal::new)
    //         .renderer(() -> RendererSignal::new);
    // public static final BlockEntityEntry<BlockEntityCombinedSignal> COMBINED_SIGNAL =
    //         COMBINED_SIGNAL_BUILDER.register();

    // private static BlockEntityBuilder<BlockEntityCombinedSignal, ?> COMBINED_SIGNAL_BUILDER = registrate()
    //         .blockEntity("main_signal",BlockEntityCombinedSignal::new)
    //         .renderer(()->RendererSignal::new);
    // public static final BlockEntityEntry<BlockEntityCombinedSignal> COMBINED_SIGNAL =
    //         COMBINED_SIGNAL_BUILDER.register();

    public static void init() {

    }

    public static void finalizeBlockEntities() {

    }
}
