package venomized.mods.extendedsignals.se.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.block.BlockWithEntityWrapper;
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
