package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityHVDistantSignal;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockHVDistantSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     */
    public BlockHVDistantSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return GermanyBlockEntities.HV_DISTANT_SIGNAL.get();
    }

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockEntityHVDistantSignal.class;
    }


}
