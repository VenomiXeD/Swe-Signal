package venomized.mods.extendedsignals.se.block.se;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class BlockGenericRotateableBlock extends SwRotateableBlock implements EntityBlock {
    public BlockGenericRotateableBlock(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion().noCollission());
    }
}
