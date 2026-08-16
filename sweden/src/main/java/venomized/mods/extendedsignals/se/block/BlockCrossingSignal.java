package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.railway.BlockCrossingObject;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;
import venomized.mods.extendedsignals.se.blockentity.auxilliarysignals.BlockEntityCrossingSignal;

public class BlockCrossingSignal extends BlockCrossingObject<BlockEntityCrossingSignal> {
    public BlockCrossingSignal(Properties properties) {
        super(properties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SwedenBlockEntities.RAILROAD_CROSSING_SIGNAL.create(pos, state);
    }

    // @Override
    // public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    //     return pBlockEntityType == SwedenBlockEntities.RAILROAD_CROSSING_SIGNAL.get() ? (level, pos, state, blockEntity) -> {
    //         ((BlockEntityRailroadCrossingSignal) blockEntity).tick(level, pos, state, blockEntity);
    //     } : null;
    // }
}
