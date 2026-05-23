package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

public class BlockModernEndpointSignal extends BlockSignal {
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
        return ExtendedSignalsSwedenBlockEntities.ENDPOINT_SIGNAL.create(pPos, pState);
    }
}
