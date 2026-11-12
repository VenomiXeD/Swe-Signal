package venomized.mods.extendedsignals.se.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.se.blockentity.BlockEntityATCController;

public class BlockATCBalise extends BlockModelled implements IBE<BlockEntityATCController> {
    /**
     * @param pProperties
     */
    public BlockATCBalise(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityATCController> getBlockEntityClass() {
        return BlockEntityATCController.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityATCController> getBlockEntityType() {
        return SwedenBlocks.ATC_CONTROLLER.right().get();
    }
}
