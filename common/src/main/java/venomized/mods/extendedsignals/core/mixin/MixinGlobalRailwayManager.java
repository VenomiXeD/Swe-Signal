package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.GlobalRailwayManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GlobalRailwayManager.class, remap = false)
public abstract class MixinGlobalRailwayManager {
    // @Inject(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V", ordinal = 0,  shift = At.Shift.AFTER))
    // public void extendedSignals$repopulateReservations(Level level, CallbackInfo ci) {
    //     InterlockingManager.groupOwnerships.forEach((uuid, signalBoundaryBooleanPair) -> {
    //         SignalEdgeGroup edgeGroup = Create.RAILWAYS.signalEdgeGroups.get(uuid);
    //         if (edgeGroup == null) return;

    //         edgeGroup.reserved = signalBoundaryBooleanPair.boundary();
    //     });
    // }
}
