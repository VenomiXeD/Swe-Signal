package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockMainSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     **/
    public BlockMainSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockMainSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return GermanyBlockEntities.MAIN_SIGNAL.get();
    }
}
