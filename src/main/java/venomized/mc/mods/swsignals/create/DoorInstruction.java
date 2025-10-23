package venomized.mc.mods.swsignals.create;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorMovementBehaviour;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import net.createmod.catnip.data.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.util.ITrainDoorData;

import java.util.List;

public class DoorInstruction extends ScheduleInstruction {
    /**
     *
     */
    public DoorInstruction() {
        super();
    }


    /**
     * @return
     */
    @Override
    public boolean supportsConditions() {
        return true;
    }

    /**
     * @param runtime
     * @param level
     * @return
     */
    @Override
    public @Nullable DiscoveredPath start(ScheduleRuntime runtime, Level level) {
        runtime.state = ScheduleRuntime.State.IN_TRANSIT;
        ((ITrainDoorData)runtime.train).swe_Signal$setDoorForcedClosed(true);
        runtime.train.carriages.forEach(carriage -> {
            carriage.forEachPresentEntity(presentEntity -> {
                presentEntity.getContraption().forEachActor(level, (mb,mc)->{
                    if (mb instanceof SlidingDoorMovementBehaviour sdmb) {
                        sdmb.tick(mc); // Tick once to update the state of the doors.
                    }
                });
            });
        });
        // runtime.currentEntry++;
        runtime.destinationReached();
        return null;
    }

    /**
     * @return
     */
    @Override
    public Pair<ItemStack, Component> getSummary() {
        return Pair.of(AllBlocks.TRAIN_DOOR.asStack(), Component.literal("TEST"));
    }

    /**
     * @return
     */
    @Override
    public ResourceLocation getId() {
        return SwSignal.modLoc("door");
    }

    /**
     * @param type
     * @return
     */
    @Override
    public List<Component> getTitleAs(String type) {
        return List.of(Component.literal("TEST TITLE" + type));
    }
}
