package venomized.mc.mods.swsignals.blockentity.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntitySignal;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityThreeLightDistantSignal extends BlockEntitySignal {
    public BlockEntityThreeLightDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState, 5);// Trick it - so it can compute distant signal states
    }

    @Override
    public SwedishSignalAspect getCurrentDisplayingAspect() {
        SwedishSignalAspect aspect = super.getCurrentDisplayingAspect();
        if (aspect == null) {
            return null;
        }
        // Remap to distant signal aspects
        return switch (aspect) {
            case STOP -> SwedishSignalAspect.PROCEED_80_EXPECT_STOP;
            case PROCEED_80 -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_80;
            case PROCEED_40_CAUTION -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;
            default -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_80;
        };
    }
}
