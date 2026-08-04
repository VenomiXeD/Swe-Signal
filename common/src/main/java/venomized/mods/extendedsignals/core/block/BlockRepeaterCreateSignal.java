package venomized.mods.extendedsignals.core.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
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
