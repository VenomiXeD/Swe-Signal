package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityHVCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVCombinedSignal extends BlockGermanySignal {
    /**
     * @param pProperties
     */
    public BlockHVCombinedSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return GermanyBlockEntities.HV_COMBINED_SIGNAL.create(pos, state);
    }
}
