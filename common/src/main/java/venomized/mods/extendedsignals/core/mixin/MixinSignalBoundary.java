package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.IRawSignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.SignalBoundaryConfiguration;

import java.util.Objects;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary, IRawSignalStateEvaluator {
    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Override
    public void extendedSignal$onScout(final Direction.AxisDirection direction, RawSignalState newState, final Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level).updateState(getId(), Objects.requireNonNullElse(
                newState,
                new RawSignalState().setAxisDirection(direction)
        ));
    }

    /**
     * @param direction
     * @param train     Train crossing over
     */
    @Override
    public void extendedSignal$onCrossed(Direction.AxisDirection direction, Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(getId(),
                        new RawSignalState()
                                .setProceed(false)
                                .setAxisDirection(direction)
                );
    }

    /**
     * @return
     */
    @Override
    public SignalBoundaryConfiguration extendedSignals$getPositiveSignalBoundaryConfiguration() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SignalBoundaryConfiguration extendedSignals$getNegativeSignalBoundaryConfiguration() {
        return null;
    }

    /**
     * @param upcomingSignal
     * @param reserved
     * @return
     */
    @Override
    public RawSignalState computeRawSignalState(Direction.AxisDirection axisDirection, RawSignalState upcomingSignal, boolean reserved) {
        if (this.cachedStates.get(axisDirection == Direction.AxisDirection.POSITIVE) == SignalBlockEntity.SignalState.RED && !reserved)
            return new RawSignalState()
                    .setAxisDirection(axisDirection)
                    .setNextState(upcomingSignal);

        return new RawSignalState()
                .setProceed(true)
                .setAxisDirection(axisDirection)
                .setNextState(upcomingSignal);
    }
}
