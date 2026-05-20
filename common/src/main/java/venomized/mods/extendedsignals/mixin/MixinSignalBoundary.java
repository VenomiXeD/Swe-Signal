package venomized.mods.extendedsignals.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.RawSignalState;
import venomized.mods.extendedsignals.create.IExtendedSignalBoundary;

import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary {

    /**
     * @return
     */
    @Override
    public UUID extendedSignal$getSignalBoundaryID() {
       return ((SignalBoundary)(Object)this).id;
    }

    /**
     *
     */
    @Override
    public void extendedSignal$onScout(Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(this.extendedSignal$getSignalBoundaryID(),
                        new RawSignalState()
                                .withProceed(true));
    }

    /**
     * @param train
     */
    @Override
    public void extendedSignal$onCrossed(Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(this.extendedSignal$getSignalBoundaryID(),
                        new RawSignalState()
                                .withProceed(false));
    }
}
