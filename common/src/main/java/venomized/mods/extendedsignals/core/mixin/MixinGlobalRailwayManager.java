package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;

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
