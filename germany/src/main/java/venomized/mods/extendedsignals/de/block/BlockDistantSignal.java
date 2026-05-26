package venomized.mods.extendedsignals.de.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityDistantSignal;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockDistantSignal extends BlockGermanySignal implements IBE {
    /**
     * @param pProperties
     */
    public BlockDistantSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return GermanyBlockEntities.DISTANT_SIGNAL.get();
    }

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockEntityDistantSignal.class;
    }


}
