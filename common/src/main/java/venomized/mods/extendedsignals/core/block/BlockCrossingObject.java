package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.state.BlockBehaviour;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;

public abstract class BlockCrossingObject<T extends BlockEntityCrossingObject> extends BlockModelled implements IBE<T> {
    public BlockCrossingObject(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
