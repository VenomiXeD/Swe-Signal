package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityHVCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVCombinedSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     */
    public BlockHVCombinedSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockEntityHVCombinedSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return GermanyBlockEntities.COMBINED_SIGNAL.get();
    }
}
