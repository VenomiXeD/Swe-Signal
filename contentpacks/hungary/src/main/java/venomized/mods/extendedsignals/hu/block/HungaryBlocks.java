package venomized.mods.extendedsignals.hu.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.hu.ExtendedSignalsHungary;
import venomized.mods.extendedsignals.hu.block.signals.BlockABS3LightSignal;
import venomized.mods.extendedsignals.hu.block.signals.BlockInterlocked2LightSignal;
import venomized.mods.extendedsignals.hu.blockentity.signs.BlockEntityDistantSignalIndicator;

public final class HungaryBlocks {
    public static Registrate registrate() {
        return ExtendedSignalsHungary.REGISTRATE.get();
    }

    public static final BlockEntry<BlockModelled> MAIN_SIGNAL_INDICATOR = RegistrateHelper.modelledBlock(registrate(), "hu", "signs", "distant_signal_indicator", BlockModelled::modelBlockEntity, BlockEntityDistantSignalIndicator::new)
            .lang("Distant Signal Indicator")
            .register();

    public static final BlockEntry<BlockInterlocked2LightSignal> INTERLOCKED_2_LIGHT_SIGNAL = RegistrateHelper.modelledBlock(registrate(), "hu", "signals", "interlocked_2_signal", BlockInterlocked2LightSignal::new)
            .lang("2 Light Signal (Interlocked)")
            .register();

    public static final BlockEntry<BlockABS3LightSignal> ABS_3_LIGHT_SIGNAL = RegistrateHelper.modelledBlock(registrate(), "hu", "signals", "abs_3_signal", BlockABS3LightSignal::new)
            .lang("3 Light Signal (Automatic Block Signal)")
            .register();

    public static void init() {
    }
}
