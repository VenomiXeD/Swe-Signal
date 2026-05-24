package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.ExtendedSignalsCoreBlockEntity;

public class ExtendedSignalsBlock extends Block {

    /**
     * @param pProperties
     */
    public ExtendedSignalsBlock(Properties pProperties) {
        super(pProperties);
    }

    private static float getSnappedRotation(BlockPos blockPos, Vec3 targetPos) {
        double dx = targetPos.x - (blockPos.getX() + 0.5);
        double dz = targetPos.z - (blockPos.getZ() + 0.5);

        double angle = Math.toDegrees(Math.atan2(dz, dx));
        angle = angle + 90.0;
        angle = (angle + 360.0) % 360.0;
        angle = Math.round(angle / 22.5) * 22.5;

        return (float) angle;
    }

    /**
     * @param pLevel
     * @param pPos
     * @param pState
     * @param pPlacer
     * @param pStack
     */
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pPlacer == null)
            return;

        if (pLevel.getBlockEntity(pPos) instanceof ExtendedSignalsCoreBlockEntity rotateableBlockEntity) {
            rotateableBlockEntity.setYOrientation(
                    getSnappedRotation(pPos, pPlacer.position())
            );
            rotateableBlockEntity.updateSelf();
        }
    }
}
