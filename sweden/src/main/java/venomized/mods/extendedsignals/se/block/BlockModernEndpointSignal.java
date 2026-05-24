package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.ExtendedSignalsBlock;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockModernEndpointSignal extends ExtendedSignalsBlock implements EntityBlock {
    public BlockModernEndpointSignal(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.ENDPOINT_SIGNAL.create(pPos, pState);
    }
}
