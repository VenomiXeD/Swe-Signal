package venomized.mods.extendedsignals.core.block.railway;

import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityLineSpeedModifier;

public class BlockLineSpeedModifier extends EdgePointBlock<BlockEntityLineSpeedModifier> {
    /**
     * @param pProperties
     */
    public BlockLineSpeedModifier(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityLineSpeedModifier> getBlockEntityClass() {
        return BlockEntityLineSpeedModifier.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityLineSpeedModifier> getBlockEntityType() {
        return CoreBlockEntities.LINE_SPEED_MODIFIER.get();
    }
}
