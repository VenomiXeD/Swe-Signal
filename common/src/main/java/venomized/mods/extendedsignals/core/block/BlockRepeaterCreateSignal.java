package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityRepeaterCreateSignal;

public class BlockRepeaterCreateSignal extends EdgePointBlock<BlockEntityRepeaterCreateSignal> {
    /**
     * @param pProperties
     */
    public BlockRepeaterCreateSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityRepeaterCreateSignal> getBlockEntityClass() {
        return BlockEntityRepeaterCreateSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityRepeaterCreateSignal> getBlockEntityType() {
        return CoreBlockEntities.SIGNAL_REPEATER.get();
    }
}
