package venomized.mods.extendedsignals.se.block.signals.tgoj;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.block.BlockSwedenSignal;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockTGOJ2BlockSignal extends BlockSwedenSignal {
    /**
     * @param pProperties
     */
    public BlockTGOJ2BlockSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SwedenBlockEntities.TGOJ_BLOCK_SIGNAL.create(pos, state);
    }
}
