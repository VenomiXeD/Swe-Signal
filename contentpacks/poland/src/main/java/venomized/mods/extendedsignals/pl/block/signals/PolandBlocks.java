package venomized.mods.extendedsignals.pl.block.signals;

import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.registry.BlockBlockEntityCoEntry;
import venomized.mods.extendedsignals.pl.ExtendedSignalsPoland;
import venomized.mods.extendedsignals.pl.blockentity.signals.BlockEntitySemiAuto5Signal;

public class PolandBlocks {

    public static final BlockBlockEntityCoEntry<BlockSignal, BlockEntitySignal<?>> TEST = ExtendedSignalsPoland.REGISTRATE.get()
            .blockSignal("pl", "signals", "semi_auto_5_signal", BlockEntitySemiAuto5Signal::new)
            .register();

    public static void init() {
    }
}
