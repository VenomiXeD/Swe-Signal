package venomized.mods.extendedsignals.se.block;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import venomized.mods.extendedsignals.block.ExtendedSignalBlock;

public abstract class BlockGenericRotateableBlock extends ExtendedSignalBlock implements EntityBlock {
    public BlockGenericRotateableBlock(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion().noCollission());
    }
}
