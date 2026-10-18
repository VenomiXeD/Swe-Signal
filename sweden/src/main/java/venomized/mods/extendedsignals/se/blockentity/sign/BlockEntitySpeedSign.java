package venomized.mods.extendedsignals.se.blockentity.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityModelled;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySingleTextModel;

public class BlockEntitySpeedSign extends BlockEntitySingleTextModel {
    public BlockEntitySpeedSign(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public Vector3f corner0() {
        return new Vector3f(4f / 16f, 9f / 16f, -0.55f / 16f);
    }

    /**
     * @return
     */
    @Override
    public Vector3f corner1() {
        return new Vector3f(-4f / 16f, 2.9f / 16f, -0.55f / 16f);
    }


}
