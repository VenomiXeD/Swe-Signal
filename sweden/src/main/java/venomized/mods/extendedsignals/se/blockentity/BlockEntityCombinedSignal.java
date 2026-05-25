package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;

public class BlockEntityCombinedSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntityCombinedSignal {
    public BlockEntityCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalBlockEntity
     * @return
     */
    @Override
    public ICombinedSignalAspect interpret(@Nullable BlockEntitySignal<?> signalBlockEntity) {
        return null;
    }

}
