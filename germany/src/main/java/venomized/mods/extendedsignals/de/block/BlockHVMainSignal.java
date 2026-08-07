package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVMainSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     **/
    public BlockHVMainSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockHVMainSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return GermanyBlockEntities.HV_MAIN_SIGNAL.get();
    }
}
