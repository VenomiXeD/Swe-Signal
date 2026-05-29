package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.signalling.CombinedSignalAspectCompositor;

public class BlockEntityHVCombinedZs3Signal extends BlockEntitySignal<ISignalAspect> {
    public BlockEntityHVCombinedZs3Signal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                // Main Signal parts
                new SignalLightPlacement(2.75d / 16d, 114 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),
                new SignalLightPlacement(2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),
                new SignalLightPlacement(-2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),

                new SignalLightPlacement(-2.75d / 16d, 105.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f),
                new SignalLightPlacement(2.75d / 16d, 100.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f),

                new SignalLightPlacement(2.75d / 16d, 96.25 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),

                // Distant signal parts
                new SignalLightPlacement(-2.25 / 16d, 77d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
                new SignalLightPlacement(-6.25 / 16d, 77d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),

                new SignalLightPlacement(6 / 16d, 67.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
                new SignalLightPlacement(2 / 16d, 67.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
        };
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull ISignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return new CombinedSignalAspectCompositor(state, incomingDirection);
    }
}
