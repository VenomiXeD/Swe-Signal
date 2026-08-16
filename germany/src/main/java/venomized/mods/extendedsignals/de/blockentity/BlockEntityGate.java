package venomized.mods.extendedsignals.de.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityGate extends BlockEntityCrossingGate {
    public BlockEntityGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public float getArmMovementTimeTicks() {
        return 20 * 6;
    }

    /**
     * @return
     */
    @Override
    public PartialModel getCrossingArmModel() {
        return GermanyModels.GATE_ARM;
    }

    /**
     * @return
     */
    @Override
    public double getArmRotationHeightPoint() {
        return 8f / 16f;
    }
}
