package venomized.mods.extendedsignals.de.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVMainBlockSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     */
    public BlockHVMainBlockSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return GermanyBlockEntities.BLOCK_SIGNAL.create(pPos, pState);
    }
}
