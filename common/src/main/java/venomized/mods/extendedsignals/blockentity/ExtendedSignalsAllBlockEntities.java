package venomized.mods.extendedsignals.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.ExtendedSignalsCore;
import venomized.mods.extendedsignals.block.ExtendedSignalsAllBlocks;
import venomized.mods.extendedsignals.client.blockentityrenderer.RendererATCController;

public class ExtendedSignalsAllBlockEntities {
    public static final BlockEntityEntry<BlockEntityRailroadCrossingController> BE_SE_RAILROAD_CROSSING_CONTROLLER =
            ExtendedSignalsAllBlockEntities.simpleBlockEntity(
                    "be_se_crossing_controller",
                    BlockEntityRailroadCrossingController::new,
                    ExtendedSignalsAllBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER
            ).register();
    public static final BlockEntityEntry<BlockEntityATCController> BE_ATC_CONTROLLER =
            simpleBlockEntity("be_se_atc_controller", BlockEntityATCController::new, ExtendedSignalsAllBlocks.BLOCK_ATC_CONTROLLER)
                    .renderer(() -> RendererATCController::new)
                    .register();
    public static BlockEntityEntry<BlockEntityTrainConfig> TRAIN_CONFIG = simpleBlockEntity(
            "be_trainconfig",
            BlockEntityTrainConfig::new,
            ExtendedSignalsAllBlocks.BLOCK_TRAIN_CONFIG
    ).register();

    public static <T extends BlockEntity> BlockEntityBuilder<T, Registrate> simpleBlockEntity(String beName, BlockEntityBuilder.BlockEntityFactory<T> beFactory, NonNullSupplier<? extends Block> validBlock) {
        return ExtendedSignalsCore.REGISTRATE.get()
                .blockEntity(beName, beFactory)
                .validBlock(validBlock);
    }

    public static void init() {

    }
}
