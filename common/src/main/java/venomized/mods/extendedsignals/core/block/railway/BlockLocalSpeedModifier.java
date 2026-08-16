package venomized.mods.extendedsignals.core.block.railway;

import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityLineSpeedModifier;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityLocalSpeedModifier;

public class BlockLocalSpeedModifier extends EdgePointBlock<BlockEntityLocalSpeedModifier> {
    /**
     * @param pProperties
     */
    public BlockLocalSpeedModifier(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityLocalSpeedModifier> getBlockEntityClass() {
        return BlockEntityLocalSpeedModifier.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<? extends BlockEntityLocalSpeedModifier> getBlockEntityType() {
        return CoreBlockEntities.LOCAL_SPEED_MODIFIER.get();
    }
}
