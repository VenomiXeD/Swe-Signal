package venomized.mods.extendedsignals.core.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.block.CoreBlocks;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPathTrainDetector;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityRepeaterCreateSignal;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntitySignalSpeedModifier;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererATCController;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;

public final class CoreBlockEntities {
    private static Registrate registrate() {
        return ExtendedSignalsCore.REGISTRATE.get();
    }

    public static final BlockEntityEntry<BlockEntityCrossingController> CROSSING_CONTROLLER = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_controller", BlockEntityCrossingController::new, CoreBlocks.CROSSING_CONTROLLER)
            .register();

    public static final BlockEntityEntry<BlockEntityATCController> ATC_CONTROLLER = RegistrateHelper
            .simpleBlockEntity(registrate(), "atc_controller", BlockEntityATCController::new, CoreBlocks.ATC_CONTROLLER)
            .renderer(() -> RendererATCController::new)
            .register();

    public static final BlockEntityEntry<BlockEntityRepeaterCreateSignal> SIGNAL_REPEATER = RegistrateHelper
            .simpleBlockEntity(registrate(), "repeater_signal", BlockEntityRepeaterCreateSignal::new, CoreBlocks.REPEATER_SIGNAL)
            .register();

    public static final BlockEntityEntry<BlockEntitySignalSpeedModifier> MODIFIER_SPEED = RegistrateHelper
            .simpleBlockEntity(registrate(), "modifier_speed", BlockEntitySignalSpeedModifier::new, CoreBlocks.SPEED_MODIFIER)
            .register();

    public static BlockEntityEntry<BlockEntityTrainConfig> TRAIN_CONFIG = RegistrateHelper
            .simpleBlockEntity(registrate(), "train_config", BlockEntityTrainConfig::new, CoreBlocks.BLOCK_TRAIN_CONFIG)
            .register();

    public static BlockEntityEntry<BlockEntityPathTrainDetector> PATH_TRAIN_DETECTOR = RegistrateHelper
            .simpleBlockEntity(registrate(), "path_train_detector", BlockEntityPathTrainDetector::new, CoreBlocks.PATH_TRAIN_DETECTOR)
            .register();

    public static void init() {

    }

    public static void finalizeBlockEntities() {

    }
}
