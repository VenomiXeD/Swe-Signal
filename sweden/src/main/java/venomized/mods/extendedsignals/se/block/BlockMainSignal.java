package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockMainSignal extends BlockSwedenSignal {
    /**
     * @param pProperties
     * @param signalLightCount
     */
    public BlockMainSignal(Properties pProperties, int signalLightCount) {
        super(pProperties, signalLightCount);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.MAIN_SIGNAL.get()
                .create(pPos, pState);
    }
}
