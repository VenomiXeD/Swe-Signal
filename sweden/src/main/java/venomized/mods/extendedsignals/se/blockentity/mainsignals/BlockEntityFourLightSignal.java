package venomized.mods.extendedsignals.se.blockentity.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityFourLightSignal extends BlockEntityMainSignal {
    public BlockEntityFourLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState, 4);
    }
}
