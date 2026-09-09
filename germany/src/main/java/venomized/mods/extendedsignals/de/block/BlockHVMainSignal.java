package venomized.mods.extendedsignals.de.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVMainSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     **/
    public BlockHVMainSignal(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return GermanyBlockEntities.HV_MAIN_SIGNAL.create(pos, state);
    }
}
