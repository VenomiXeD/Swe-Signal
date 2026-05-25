package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockCombinedSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     * @param signalLightCount
     */
    public BlockCombinedSignal(Properties pProperties) {
        super(pProperties, -1);
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

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockEntityCombinedSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return GermanyBlockEntities.COMBINED_SIGNAL.get();
    }
}
