package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityAbstractSignalBox;

public class BlockEntitySignalBox extends BlockEntityAbstractSignalBox {
    public BlockEntitySignalBox(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
}
