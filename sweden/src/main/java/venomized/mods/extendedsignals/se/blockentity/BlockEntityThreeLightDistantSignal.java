package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;

public class BlockEntityThreeLightDistantSignal extends BlockEntitySignal {
    public BlockEntityThreeLightDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[0];
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public ISignalAspect interpret(RawSignalState rawState) {
        return null;
    }
}
