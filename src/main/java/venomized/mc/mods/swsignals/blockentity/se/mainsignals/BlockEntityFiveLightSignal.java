package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityFiveLightSignal extends BlockEntitySignal {
    public BlockEntityFiveLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState, 5);
    }
}
