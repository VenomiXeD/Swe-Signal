package venomized.mods.extendedsignals.de.block.hvk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.block.BlockGermanySignal;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVKBlockSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     */
    public BlockHVKBlockSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return GermanyBlockEntities.HVKBlockEntities.HVK_BLOCK_SIGNAL.create(pos, state);
    }
}
