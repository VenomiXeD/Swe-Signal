package venomized.mods.extendedsignals.se.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.core.block.BlockCrossingObject;
import venomized.mods.extendedsignals.se.blockentity.auxilliarysignals.BlockEntityCrossingSignal;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockCrossingSignal extends BlockCrossingObject<BlockEntityCrossingSignal> {
    public BlockCrossingSignal(Properties properties) {
        super(properties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityCrossingSignal> getBlockEntityClass() {
        return BlockEntityCrossingSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityCrossingSignal> getBlockEntityType() {
        return SwedenBlockEntities.RAILROAD_CROSSING_SIGNAL.get();
    }

    // @Override
    // public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    //     return pBlockEntityType == SwedenBlockEntities.RAILROAD_CROSSING_SIGNAL.get() ? (level, pos, state, blockEntity) -> {
    //         ((BlockEntityRailroadCrossingSignal) blockEntity).tick(level, pos, state, blockEntity);
    //     } : null;
    // }
}
