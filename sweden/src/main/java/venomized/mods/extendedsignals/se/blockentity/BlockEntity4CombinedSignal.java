package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public class BlockEntity4CombinedSignal extends BlockEntitySignal<ICombinedSignalAspect> {
    public BlockEntity4CombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(0, 40.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 19.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0)
        };
    }

    /**
     * @param state
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(RawSignalState state) {
        return (a, b) -> {
        };
    }

}
