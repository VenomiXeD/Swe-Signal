package venomized.mods.extendedsignals.se.blockentity;

import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

public class BlockEntity4CombinedSignal extends BlockEntitySignal<ICombinedSignalAspect> {
    public static Combined4SignalStateMapper COMBINED_4_SIGNAL_MAPPER = SignalStateRemapper.register(new Combined4SignalStateMapper(ExtendedSignalsSweden.res("4_combined_signal")));

    public BlockEntity4CombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param referenceProvider
     */
    @Override
    public void bindToCreateSignal(ISignalBoundaryReferenceProvider referenceProvider) {
        super.bindToCreateSignal(referenceProvider);
        TrackEdgePoint point = referenceProvider.getTrackTargetingBehavior().getEdgePoint();
        if (point instanceof IExtendedSignalBoundary<?> boundary) {
            boundary.setMapper(referenceProvider.getTrackTargetingBehavior().getTargetDirection() == Direction.AxisDirection.POSITIVE,
                    COMBINED_4_SIGNAL_MAPPER
            );
        }
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
     * @param direction
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return (ticks, lights) -> {
            if (state.isStop()) {
                ISignalAspect.RGB.BLACK.apply(lights[0]);
                ISignalAspect.RGB.RED.apply(lights[1]);
                ISignalAspect.RGB.BLACK.apply(lights[2]);
                ISignalAspect.RGB.BLACK.apply(lights[3]);
                return;
            }

            for (SignalLightState light : lights)
                ISignalAspect.RGB.BLACK.apply(light);

            if (state.getMaxProceedSpeed() > 40) {
                ISignalAspect.RGB.GREEN.apply(lights[0]);
            } else {
                ISignalAspect.RGB.GREEN.apply(lights[0]);
                ISignalAspect.RGB.GREEN.apply(lights[2]);
            }

            SignalStateNode distant = state.getNextState();
            boolean blink = ticks % 20 > 10;
            if (distant == null)
                return;

            if (distant.isStop()) {
                (blink ? ISignalAspect.RGB.BLACK : ISignalAspect.RGB.GREEN).apply(lights[2]);
                return;
            }

            // Next is showing 40, 4-light signal has no way to display that, so proceed 40 is used instead
            if (distant.getMaxProceedSpeed() <= 40) {
                ISignalAspect.RGB.GREEN.apply(lights[2]);
                return;
            }

            (blink ? ISignalAspect.RGB.BLACK : ISignalAspect.RGB.WHITE).apply(lights[3]);
        };
        // return CombinedSignalAspectCompositor.interpret(state);
    }

    public static class Combined4SignalStateMapper extends SignalStateRemapper {
        public Combined4SignalStateMapper(ResourceLocation id) {
            super(id);
        }

        @Override
        public SignalStateNode remap(SignalStateNode old) {
            // Since a 4 light signal cannot display expect proceed 40, we'll remap it to proceed 40
            SignalStateNode distant = old.getNextState();
            if (distant == null)
                return old;

            if (distant.getMaxProceedSpeed() <= 40)
                old.setMaxProceedSpeed(distant.getMaxProceedSpeed());

            return old;
        }
    }

}
