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
import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary, IRawSignalStateEvaluator {
    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Shadow
    public Couple<UUID> groups;

    /**
     * @return
     */
    @Override
    public SignalBoundaryConfiguration getPositiveSignalBoundaryConfiguration() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SignalBoundaryConfiguration getNegativeSignalBoundaryConfiguration() {
        return null;
    }

    /**
     * @param upcomingSignal
     * @return
     */
    @Override
    public RawSignalState computeRawSignalState(Direction.AxisDirection axisDirection, RawSignalState upcomingSignal, Train train) {
        if (this.cachedStates.get(axisDirection == Direction.AxisDirection.POSITIVE) == SignalBlockEntity.SignalState.RED &&
                train.reservedSignalBlocks.contains(this.groups.get(axisDirection == Direction.AxisDirection.POSITIVE)))
            return new RawSignalState().setReserved(false);

        return new RawSignalState()
                .setProceed(true)
                .setReserved(true);
    }

    /**
     * @return
     */
    @Override
    public boolean skipChaining() {
        return false;
    }
}
