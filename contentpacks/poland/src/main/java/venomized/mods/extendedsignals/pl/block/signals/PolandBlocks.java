package venomized.mods.extendedsignals.pl.block.signals;

import net.neoforged.neoforge.registries.DeferredRegister;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.registry.BlockBlockEntityCoEntry;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.pl.ExtendedSignalsPoland;
import venomized.mods.extendedsignals.pl.blockentity.signals.BlockEntitySemiAuto5Signal;

public class PolandBlocks {
    public static final BlockBlockEntityCoEntry<BlockSignal, BlockEntitySignal<IMainSignalAspect>> SEMI_AUTO_5L_SIGNAL = ExtendedSignalsPoland.REGISTRATE.get()
            .blockSignal("pl", "signals", "semi_auto_5_signal", BlockEntitySemiAuto5Signal::new)
            .register();

    public static void init() {
    }
}
