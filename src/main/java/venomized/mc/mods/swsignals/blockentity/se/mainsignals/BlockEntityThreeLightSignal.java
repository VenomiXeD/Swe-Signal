package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityThreeLightSignal extends BlockEntitySignal {
    public BlockEntityThreeLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState, 3);
    }

    @Override
    public SwedishSignalAspect getCurrentDisplayingAspect() {
        SwedishSignalAspect displayingAspect = super.getCurrentDisplayingAspect();
        if (displayingAspect == SwedishSignalAspect.PROCEED_80_EXPECT_STOP || displayingAspect == SwedishSignalAspect.PROCEED_40_CAUTION) {
            return SwedishSignalAspect.PROCEED_40_CAUTION;
        }
        return displayingAspect;
    }
}
