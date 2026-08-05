package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockCrossingObject;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityCrossingDistantSignal;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockCrossingDistantSignal extends BlockCrossingObject<BlockEntityCrossingDistantSignal> {
    public BlockCrossingDistantSignal(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityCrossingDistantSignal> getBlockEntityClass() {
        return BlockEntityCrossingDistantSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityCrossingDistantSignal> getBlockEntityType() {
        return SwedenBlockEntities.RAILROAD_CROSSING_DISTANT_SIGNAL.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.RAILROAD_CROSSING_DISTANT_SIGNAL.create(pPos, pState);
    }

    // @Override
    // public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    //     return pBlockEntityType == SwedenBlockEntities.RAILROAD_CROSSING_DISTANT_SIGNAL.get() ? (level, pos, state, blockEntity) -> {
    //         ((BlockEntityRailroadCrossingDistantSignal) blockEntity).tick(level, pos, state, blockEntity);
    //     } : null;
    // }
}
