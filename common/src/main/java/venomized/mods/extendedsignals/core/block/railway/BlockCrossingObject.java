package venomized.mods.extendedsignals.core.block.railway;

import net.minecraft.world.level.block.state.BlockBehaviour;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;

public abstract class BlockCrossingObject<T extends BlockEntityCrossingObject> extends BlockModelled {
    public BlockCrossingObject(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
