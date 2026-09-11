package venomized.mods.extendedsignals.de.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
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
    public float gateArmMovementTimeTicks() {
        return 20 * 6;
    }

    /**
     * @return
     */
    @Override
    public PartialModel gateArmModel() {
        return GermanyModels.GATE_ARM;
    }

    /**
     * @return
     */
    @Override
    public Vector3f gateArmPivotPoint() {
        return new Vector3f(0, 8f / 16f, 0);
    }
}
