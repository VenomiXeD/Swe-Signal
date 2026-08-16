package venomized.mods.extendedsignals.core.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.block.CoreBlocks;
import venomized.mods.extendedsignals.core.blockentity.railway.*;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;

public final class CoreBlockEntities {
    public static final BlockEntityEntry<BlockEntityCrossingController> CROSSING_CONTROLLER = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_controller", BlockEntityCrossingController::new, CoreBlocks.CROSSING_CONTROLLER)
            .register();
    public static final BlockEntityEntry<BlockEntityRepeaterCreateSignal> SIGNAL_REPEATER = RegistrateHelper
            .simpleBlockEntity(registrate(), "repeater_signal", BlockEntityRepeaterCreateSignal::new, CoreBlocks.REPEATER_SIGNAL)
            .register();

    // public static final BlockEntityEntry<BlockEntityATCController> ATC_CONTROLLER = RegistrateHelper
    //         .simpleBlockEntity(registrate(), "atc_controller", BlockEntityATCController::new, CoreBlocks.ATC_CONTROLLER)
    //         .renderer(() -> RendererATCController::new)
    //         .register();
    public static final BlockEntityEntry<BlockEntityLocalSpeedModifier> LOCAL_SPEED_MODIFIER = RegistrateHelper
            .simpleBlockEntity(registrate(), "local_modifier_speed", BlockEntityLocalSpeedModifier::new, CoreBlocks.LOCAL_SPEED_MODIFIER)
            .register();

    public static final BlockEntityEntry<BlockEntityLineSpeedModifier> LINE_SPEED_MODIFIER = RegistrateHelper
            .simpleBlockEntity(registrate(), "line_modifier_speed", BlockEntityLineSpeedModifier::new, CoreBlocks.LINE_SPEED_MODIFIER)
            .register();
    public static BlockEntityEntry<BlockEntityTrainConfig> TRAIN_CONFIG = RegistrateHelper
            .simpleBlockEntity(registrate(), "train_config", BlockEntityTrainConfig::new, CoreBlocks.BLOCK_TRAIN_CONFIG)
            .register();
    public static BlockEntityEntry<BlockEntityTrainPathObserver> PATH_TRAIN_DETECTOR = RegistrateHelper
            .simpleBlockEntity(registrate(), "path_train_detector", BlockEntityTrainPathObserver::new, CoreBlocks.PATH_TRAIN_DETECTOR)
            .register();
    public static BlockEntityEntry<BlockEntityPathIdentifier> PATH_IDENTIFIER = RegistrateHelper
            .simpleBlockEntity(registrate(), "path_identifier", BlockEntityPathIdentifier::new, CoreBlocks.PATH_IDENTIFIER)
            .register();

    private static Registrate registrate() {
        return ExtendedSignals.REGISTRATE.get();
    }

    public static void init() {

    }

    public static void finalizeBlockEntities() {

    }
}
