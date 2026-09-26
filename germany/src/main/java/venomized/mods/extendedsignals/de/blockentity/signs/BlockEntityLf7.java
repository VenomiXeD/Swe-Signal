package venomized.mods.extendedsignals.de.blockentity.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySingleTextModel;

public class BlockEntityLf7 extends BlockEntitySingleTextModel {
    public BlockEntityLf7(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public Vector3f corner0() {
        return new Vector3f(2.5f / 16f, 41f / 16f, -2.1f / 16f);
    }

    /**
     * @return
     */
    @Override
    public Vector3f corner1() {
        return new Vector3f(-2.5f / 16f, 33f / 16f, -2.1f / 16f);
    }
}
