package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;

public class BlockEntityThreeLightDistantSignal extends BlockEntitySignal {
    public BlockEntityThreeLightDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }
    /**
     * @param rawState
     * @param signalBlockEntity
     * @return
     */
    @Override
    public ISignalAspect interpret(RawSignalState rawState, @Nullable BlockEntitySignal<?> signalBlockEntity) {
        return null;
    }
}
