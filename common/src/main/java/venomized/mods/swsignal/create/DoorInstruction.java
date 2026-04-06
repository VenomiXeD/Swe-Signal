package venomized.mods.swsignal.create;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorMovementBehaviour;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import net.createmod.catnip.data.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.client.sound.train.ICarriageSounds;
import venomized.mc.mods.swsignals.client.sound.train.TrainSound;
import venomized.mc.mods.swsignals.core.SwSignal;
import venomized.mc.mods.swsignals.data.SwSignalLang;
import venomized.mc.mods.swsignals.util.ITrainDoorData;

import java.util.List;
import java.util.Optional;

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
        if (level.isClientSide()) {
            runtime.train.carriages.forEach(carriage -> {
                carriage.forEachPresentEntity(presentEntity -> {
                    Optional<TrainSound> customTrainSound = ((ICarriageSounds) presentEntity.sounds).getCustomTrainSound();
                    customTrainSound.get().closingDoors(presentEntity);
                });
            });
        }

        // Lol disgusting way to do this but it works
        runtime.state = ScheduleRuntime.State.IN_TRANSIT;
        ((ITrainDoorData) runtime.train).swe_Signal$setDoorForcedClosed(true);
        runtime.train.carriages.forEach(carriage -> {
            carriage.forEachPresentEntity(presentEntity -> {
                presentEntity.getContraption().forEachActor(level, (mb, mc) -> {
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
        return Pair.of(AllBlocks.TRAIN_DOOR.asStack(), SwSignalLang.schedule("door", "summary", "Door Control"));
    }

    /**
     * @return
     */
    @Override
    public ResourceLocation getId() {
        return SwSignal.resource("door");
    }

    /**
     * @param type
     * @return
     */
    @Override
    public List<Component> getTitleAs(String type) {
        return List.of(SwSignalLang.schedule("door", "title", "Close door").copy().withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
    }
}
