package venomized.mods.extendedsignals.de.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockKsMainSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     */
    public BlockKsMainSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return GermanyBlockEntities.KS_MAIN_SIGNAL.create(pPos, pState);
    }
}
