package venomized.mods.extendedsignals.de.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockKsCombinedSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     */
    public BlockKsCombinedSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return GermanyBlockEntities.KS_COMBINED_SIGNAL.create(pos, state);
    }
}
