package venomized.mods.extendedsignals.se.block.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockCrossingObject;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;
import venomized.mods.extendedsignals.se.block.BlockGenericRotateableBlock;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityCrossingGate;

public class BlockCrossingGate extends BlockCrossingObject<BlockEntityCrossingGate> {
    public BlockCrossingGate(Properties properties) {
        super(properties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityCrossingGate> getBlockEntityClass() {
        return BlockEntityCrossingGate.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityCrossingGate> getBlockEntityType() {
        return SwedenBlockEntities.CROSSING_GATE.get();
    }

    /**
     * @param p_153212_
     * @param p_153213_
     * @param p_153214_
     * @param <S>
     * @return
     */
    @Override
    public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<S> p_153214_) {
        return p_153212_.isClientSide() && p_153214_ == SwedenBlockEntities.CROSSING_GATE.get() ? BlockEntityCrossingGate::serverTick : null;
    }

    // @Override
    // public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    //     return pLevel.isClientSide() && pBlockEntityType == SwedenBlockEntities.CROSSING_GATE.get() ?
    //             ((level, blockPos, blockState, t) -> BlockEntityCrossingGate.clientTick(level, blockPos, blockState, (BlockEntityCrossingGate) t))
    //             : null;
    // }
}
