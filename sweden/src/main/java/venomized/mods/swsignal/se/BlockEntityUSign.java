package venomized.mods.swsignal.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.ExtendedSignalBlockEntity;

public class BlockEntityUSign extends ExtendedSignalBlockEntity {
    public BlockEntityUSign(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
}
