package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;

import java.util.UUID;

@Mixin(value = SignalBlockEntity.class, remap = false)
public abstract class MixinCreateTrainSignal implements ISignalTunerToolable, ISignalBoundaryReferenceProvider {
    @Shadow
    public TrackTargetingBehaviour<SignalBoundary> edgePoint;

    @Override
    public boolean isReader() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public TrackTargetingBehaviour<?> getTrackTargetingBehavior() {
        return this.edgePoint;
    }
}
