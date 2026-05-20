package venomized.mods.extendedsignals.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.create.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.create.tracks.ATCController;
import venomized.mods.extendedsignals.util.ITrainDoorData;

import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData {
    @Unique
    private boolean swe_Signal$doorOpen = false;

    @ModifyReturnValue(method = "frontSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener frontSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            if (couple.getFirst() instanceof ATCController atcController) {
                atcController.onATCAction(((Train) (Object) this));
                return false;
            }

            if (couple.getFirst() instanceof SignalBoundary signalBoundary) {
                ((IExtendedSignalBoundary) signalBoundary)
                        .extendedSignal$onCrossed((Train) (Object) this);
            }

            return original.test(distance, couple);
        };
    }

    @Inject(method = "occupy", at = @At("HEAD"))
    private void onOccupy(UUID groupId, UUID boundaryId, CallbackInfoReturnable<Boolean> cir) {
        // SignalNetwork.onSignalUpdate(groupId, boundaryId);
    }

    /**
     * @return
     */
    @Override
    public boolean swe_Signal$doorForcedClosed() {
        return swe_Signal$doorOpen;
    }

    /**
     * @param closed
     * @return
     */
    @Override
    public boolean swe_Signal$setDoorForcedClosed(boolean closed) {
        this.swe_Signal$doorOpen = closed;
        return swe_Signal$doorOpen;
    }
}

