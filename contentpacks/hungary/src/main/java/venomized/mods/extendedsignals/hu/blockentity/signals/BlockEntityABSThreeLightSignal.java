package venomized.mods.extendedsignals.hu.blockentity.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.hu.signalling.ABS3Signal;

public class BlockEntityABSThreeLightSignal extends BlockEntityThreeLightSignal<ISignalAspect> {
    public BlockEntityABSThreeLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public boolean hasMainSignalCapability() {
        return true;
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return ABS3Signal.interpret(state);
    }
}
