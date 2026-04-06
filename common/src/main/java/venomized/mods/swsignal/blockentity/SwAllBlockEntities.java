package venomized.mods.swsignal.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mc.mods.swsignals.block.AllBlocks;
import venomized.mc.mods.swsignals.core.SwSignal;
import venomized.mods.swsignal.block.SwAllBlocks;
import venomized.mods.swsignal.client.blockentityrenderer.RendererATCController;
import venomized.mods.swsignal.core.SwSignalCore;

public class SwAllBlockEntities {
    public static BlockEntityEntry<BlockEntityTrainConfig> be_trainconfig = simpleBlockEntity(
            "be_trainconfig",
            BlockEntityTrainConfig::new,
            SwAllBlocks.BLOCK_TRAIN_CONFIG
    ).register();

    public static <T extends BlockEntity> BlockEntityBuilder<T, Registrate> simpleBlockEntity(String beName, BlockEntityBuilder.BlockEntityFactory<T> beFactory, NonNullSupplier<? extends Block> validBlock) {
        return SwSignalCore.REGISTRATE.get()
                .blockEntity(beName, beFactory)
                .validBlock(validBlock);
    }

    public static final BlockEntityEntry<BlockEntityATCController> BE_ATC_CONTROLLER =
            simpleBlockEntity("be_se_atc_controller", BlockEntityATCController::new, SwAllBlocks.BLOCK_ATC_CONTROLLER)
                    .renderer(() -> RendererATCController::new)
                    .register();
}
