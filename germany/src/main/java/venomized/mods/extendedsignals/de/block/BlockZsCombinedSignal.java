package venomized.mods.extendedsignals.de.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockZsCombinedSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     */
    public BlockZsCombinedSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return GermanyBlockEntities.ZS3_SIGNAL.create(pPos, pState);
    }
}
