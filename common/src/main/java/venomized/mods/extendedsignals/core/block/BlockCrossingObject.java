package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;

public abstract class BlockCrossingObject<T extends BlockEntityCrossingObject> extends BlockModelled implements IBE<T> {
    public BlockCrossingObject(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
