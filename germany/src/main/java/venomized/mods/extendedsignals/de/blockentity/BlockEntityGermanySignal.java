package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public abstract class BlockEntityGermanySignal<T extends ISignalAspect> extends BlockEntitySignal<T> {
    public BlockEntityGermanySignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    public float getZs3MatrixDisplaySpeed() {
        if (currentSignalState().isProceed() && currentSignalState().getMiscTags().containsKey("local_speed")) {
            return (float) (currentSignalState().getMaxProceedSpeed() / 10f);
        }
        return -1;
    }

    public float getZs3vMatrixDisplaySpeed() {
        if (currentSignalState().getReservedBy() == null)
            return -1;
        if (currentSignalState().isStop())
            return -1;
        SignalStateNode next = currentSignalState().getNextState();
        if (next == null || next.isStop())
            return -1;
        if (!next.getMiscTags().containsKey("local_speed"))
            return -1;

        // Zs3v not shown for speed increases
        if (currentSignalState().getMaxProceedSpeed() < next.getMaxProceedSpeed())
            return -1;

        if (!hasMainSignalCapability()) {
            // Passing through this code block - Signal is likely a distant signal or repeater signal and not combined
            return Mth.floor(next.getMaxProceedSpeed() / 10f);
        }
        return currentSignalState().getMaxProceedSpeed() == next.getMaxProceedSpeed() ? -1 : Mth.floor(next.getMaxProceedSpeed() / 10f);
    }
}
