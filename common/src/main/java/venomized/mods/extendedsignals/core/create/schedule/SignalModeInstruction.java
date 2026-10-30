package venomized.mods.extendedsignals.core.create.schedule;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.schedule.destination.ScheduleInstruction;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import net.createmod.catnip.data.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;
import venomized.mods.extendedsignals.core.create.SignalPolicy;

public class SignalModeInstruction extends ScheduleInstruction {
    public static final ResourceLocation ID = ExtendedSignals.res("signal_mode");

    /**
     *
     */
    public SignalModeInstruction() {
        super();
    }

    /**
     * @return
     */
    @Override
    public boolean supportsConditions() {
        return false;
    }

    /**
     * @param runtime
     * @param level
     * @return
     */
    @Override
    public @Nullable DiscoveredPath start(ScheduleRuntime runtime, Level level) {
        AdvancedSignalScout.from(runtime.train).setSignalPolicyMode(enumData("signal_mode", SignalPolicy.class));
        runtime.currentEntry++;
        return null;
    }

    /**
     * @return
     */
    @Override
    public Pair<ItemStack, Component> getSummary() {
        return Pair.of(AllBlocks.TRACK_SIGNAL.asStack(), Component.translatable("extended_signals.schedule.signal_mode.summary"));
    }


    /**
     * @return
     */
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    /**
     * @param builder
     */
    @Override
    public void initConfigurationWidgets(ModularGuiLineBuilder builder) {
        builder.addSelectionScrollInput(0, 24, (i, l) -> {
            i.forOptions(SignalPolicy.translatedOptions()).titled(null).setWidth(121);
        }, "signal_mode");
    }
}
