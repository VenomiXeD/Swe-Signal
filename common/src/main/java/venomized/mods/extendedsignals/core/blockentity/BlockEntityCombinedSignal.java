package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;

public class BlockEntityCombinedSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal<ICombinedSignalAspect> {
    public BlockEntityCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public ICombinedSignalAspect interpret(RawSignalState rawState) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return getSignalLightPlacements(
                0f, 0f / 16f, 3f, 3f, 1f,
                (19.75d / 16d), (26.75d / 16d) - (19.7d / 16d),
                ((BlockSignal) this.getBlockState()
                        .getBlock()).getSignalLightCount()
        );
    }
}
