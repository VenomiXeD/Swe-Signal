package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

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
        if (currentSignalState().isProceed() && currentSignalState().getNextState() != null && currentSignalState().getNextState().getMiscTags().containsKey("local_speed")) {

            return currentSignalState().getMaxProceedSpeed() >= currentSignalState().getNextState().getMaxProceedSpeed() ? (float) (currentSignalState().getNextState().getMaxProceedSpeed() / 10f) : -1;
        }
        return -1;
    }
}
