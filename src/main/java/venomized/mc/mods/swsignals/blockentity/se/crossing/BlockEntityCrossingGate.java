package venomized.mc.mods.swsignals.blockentity.se.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingObject;

public class BlockEntityCrossingGate extends BlockEntityRailroadCrossingObject {
    public static final int MAX_ARM_MOVEMENT_TICKS = 20 * 20; // 20 seconds to fully raise/lower the arm

    private int ARM_MOVEMENT_TICKS = 0;

    public BlockEntityCrossingGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, BlockEntityCrossingGate t) {
        t.ARM_MOVEMENT_TICKS = Mth.clamp(t.ARM_MOVEMENT_TICKS + (t.isRailroadCrossingControllerPowered() ? 1 : -1), 0, MAX_ARM_MOVEMENT_TICKS);
    }

    public int getArmMovementProgressTicks() {
        return MAX_ARM_MOVEMENT_TICKS - ARM_MOVEMENT_TICKS;
    }
}
