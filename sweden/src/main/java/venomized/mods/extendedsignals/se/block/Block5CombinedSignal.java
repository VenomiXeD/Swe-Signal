package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class Block5CombinedSignal extends BlockSwedenSignal {
    /**
     * @param pProperties
     */
    public Block5CombinedSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.COMBINED_5_SIGNAL.get()
                .create(pPos, pState);
    }
}
