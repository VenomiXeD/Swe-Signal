package venomized.mc.mods.swsignals.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.AllBlocks;

public class AllBlockEntities {
    public static BlockEntityEntry<BlockEntityTrainConfig> be_trainconfig = simpleBlockEntity(
            "be_trainconfig",
            BlockEntityTrainConfig::new,
            AllBlocks.BLOCK_TRAIN_CONFIG
    ).register();

    public static <T extends BlockEntity> BlockEntityBuilder<T, Registrate> simpleBlockEntity(String beName, BlockEntityBuilder.BlockEntityFactory<T> beFactory, NonNullSupplier<? extends Block> validBlock) {
        return SwSignal.REGISTRATE.get()
                .blockEntity(beName, beFactory)
                .validBlock(validBlock);
    }
}
