package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockMainSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     **/
    public BlockMainSignal(Properties pProperties) {
        super(pProperties, -1);
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

    /**
     * @return
     */
    @Override
    public double lightXPosition() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public double lightYPosition() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public double lightZPosition() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public double lightSeparationDistance() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public float lightXScale() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public float lightYScale() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public float lightZScale() {
        return 0;
    }
}
