package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;


public abstract class BlockEntityMainSignal extends BlockEntitySignal<IMainSignalAspect> {
    public BlockEntityMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }
}
