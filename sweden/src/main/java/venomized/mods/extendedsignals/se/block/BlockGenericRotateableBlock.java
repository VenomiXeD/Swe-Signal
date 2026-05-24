package venomized.mods.extendedsignals.se.block;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import venomized.mods.extendedsignals.core.block.ExtendedSignalsBlock;

public abstract class BlockGenericRotateableBlock extends ExtendedSignalsBlock implements EntityBlock {
    public BlockGenericRotateableBlock(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion().noCollission());
    }
}
