package venomized.mods.extendedsignals.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.schedule.Schedule;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.create.DoorInstruction;
import venomized.mods.extendedsignals.util.ITrainDoorData;

@Mixin(value = ScheduleRuntime.class, remap = false)
public class MixinScheduleRuntime {
    @Shadow
    public Train train;

    @Shadow
    public Schedule schedule;

    @Shadow
    public int currentEntry;

    @Inject(method = "destinationReached", at = @At(value = "HEAD"))
    public void onDestinationReached(CallbackInfo ci) {
        if (this.schedule == null) return;
        if (this.schedule.entries.get(this.currentEntry).instruction instanceof DoorInstruction)
            return;

        ((ITrainDoorData) train).swe_Signal$setDoorForcedClosed(false);
    }
}
