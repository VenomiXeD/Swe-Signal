package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.block.BlockRailroadCrossingObject;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityRailroadCrossingDistantSignal;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

public class BlockRailroadCrossingDistantSignal extends BlockRailroadCrossingObject {
    public BlockRailroadCrossingDistantSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ExtendedSignalsSwedenBlockEntities.BE_RAILROAD_CROSSING_DISTANT_SIGNAL.create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ExtendedSignalsSwedenBlockEntities.BE_RAILROAD_CROSSING_DISTANT_SIGNAL.get() ? (level, pos, state, blockEntity) -> {
            ((BlockEntityRailroadCrossingDistantSignal) blockEntity).tick(level, pos, state, blockEntity);
        } : null;
    }
}
