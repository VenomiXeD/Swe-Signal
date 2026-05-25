package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;

public abstract class BlockEntityCombinedSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal<ICombinedSignalAspect> {
    public BlockEntityCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }
}
