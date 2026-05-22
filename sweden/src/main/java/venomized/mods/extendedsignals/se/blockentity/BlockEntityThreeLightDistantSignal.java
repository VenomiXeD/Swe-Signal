package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.se.SwedishSignalAspect;
import venomized.mods.extendedsignals.se.blockentity.mainsignals.BlockEntitySignal;

public class BlockEntityThreeLightDistantSignal extends BlockEntitySignal {
    public BlockEntityThreeLightDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
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
