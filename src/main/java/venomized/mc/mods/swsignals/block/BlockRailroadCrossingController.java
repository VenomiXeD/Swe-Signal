package venomized.mc.mods.swsignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingController;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;

public class BlockRailroadCrossingController extends SwAbstractBlock implements EntityBlock {
    public BlockRailroadCrossingController() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK));
    }

    public BlockRailroadCrossingController(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SeBlockEntities.BE_SE_RAILROAD_CROSSING_CONTROLLER.get().create(pPos, pState);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (pLevel.isClientSide) {
            return;
        }
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof BlockEntityRailroadCrossingController rcc) {
            rcc.setPowered(pLevel.hasNeighborSignal(pPos));
        }
    }
}
