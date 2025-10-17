package venomized.mc.mods.swsignals.blockentity.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityAbstractSignalBox;

public class BlockEntitySignalBox extends BlockEntityAbstractSignalBox {
    public BlockEntitySignalBox(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
}
