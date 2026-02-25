package venomized.mc.mods.swsignals.item.components;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;
import venomized.mc.mods.swsignals.core.SwSignal;

import java.util.function.Supplier;

public class SwComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SwSignal.MOD_ID);

    public static final Supplier<DataComponentType<ISignalTunerBindable.SignalTunerMode>> SIGNAL_TUNER_MODE = COMPONENTS.registerComponentType("signal_tuner_mode", builder -> builder
            .persistent(ISignalTunerBindable.SignalTunerMode.CODEC)
            .networkSynchronized(ISignalTunerBindable.SignalTunerMode.STREAM_CODEC)
    );
    public static final Supplier<DataComponentType<BlockPos>> BIND_LOCATION = COMPONENTS.registerComponentType("bind_location", builder -> builder
            .persistent(BlockPos.CODEC)
            .networkSynchronized(BlockPos.STREAM_CODEC)
    );
}
