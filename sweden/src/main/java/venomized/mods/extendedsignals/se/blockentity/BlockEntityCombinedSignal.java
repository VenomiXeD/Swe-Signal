package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public class BlockEntityCombinedSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntityCombinedSignal {
    public BlockEntityCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public ICombinedSignalAspect interpret(RawSignalState rawState) {
        return null;
    }
}
