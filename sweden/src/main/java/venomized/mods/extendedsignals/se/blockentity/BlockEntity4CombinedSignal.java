package venomized.mods.extendedsignals.se.blockentity;

import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
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
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting()
                .withLight("l0", new SignalLight(0, 40.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l1", new SignalLight(0, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 0, 0))
                .withLight("l2", new SignalLight(0, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l3", new SignalLight(0, 19.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 255, 255));
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
    public SignalLight[] constructLightPlacements() {
        return new SignalLight[]{
                new SignalLight(0, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLight(0, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLight(0, 19.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0)
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
                lights.powered("l1");
                return;
            }

            if (state.getMaxProceedSpeed() > 40) {
                lights.powered("l0");
            } else {
                lights.powered("l0");
                lights.powered("l2");
            }

            SignalStateNode distant = state.getNextState();
            boolean blink = ticks % 20 > 10;
            if (distant == null)
                return;

            if (distant.isStop()) {
                if (blink)
                    lights.powered("l2");
                return;
            }

            // Next is showing 40, 4-light signal has no way to display that, so proceed 40 is used instead
            if (distant.getMaxProceedSpeed() <= 40) {
                lights.powered("l2");
                return;
            }

            if (blink)
                lights.powered("l3");
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
