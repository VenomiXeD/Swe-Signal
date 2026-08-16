package venomized.mods.extendedsignals.core.block.railway;

import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPathIdentifier;

public class BlockPathIdentifier extends EdgePointBlock<BlockEntityPathIdentifier> {
    /**
     * @param pProperties
     */
    public BlockPathIdentifier(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityPathIdentifier> getBlockEntityClass() {
        return BlockEntityPathIdentifier.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<? extends BlockEntityPathIdentifier> getBlockEntityType() {
        return CoreBlockEntities.PATH_IDENTIFIER.get();
    }
}
