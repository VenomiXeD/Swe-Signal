package venomized.mods.extendedsignals.hu.blockentity;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.hu.ExtendedSignalsHungary;
import venomized.mods.extendedsignals.hu.block.HungaryBlocks;
import venomized.mods.extendedsignals.hu.blockentity.signals.BlockEntityABSThreeLightSignal;
import venomized.mods.extendedsignals.hu.blockentity.signals.BlockEntityInterlockedTwoLightSignal;

public final class HungaryBlockEntities {
    public static Registrate registrate() {
        return ExtendedSignalsHungary.REGISTRATE.get();
    }

    public static final BlockEntityEntry<BlockEntityInterlockedTwoLightSignal> INTERLOCKED_TWO_LIGHT_SIGNAL = RegistrateHelper.simpleBlockEntity(
                    registrate(), "interlocked_2_signal", BlockEntityInterlockedTwoLightSignal::new, HungaryBlocks.INTERLOCKED_2_LIGHT_SIGNAL
            )
            .renderer(() -> RendererSignal::new)
            .register();

    public static final BlockEntityEntry<BlockEntityABSThreeLightSignal> ABS_THREE_LIGHT_SIGNAL = RegistrateHelper.simpleBlockEntity(
                    registrate(), "abs_3_signal", BlockEntityABSThreeLightSignal::new, HungaryBlocks.ABS_3_LIGHT_SIGNAL
            )
            .renderer(() -> RendererSignal::new)
            .register();

    public static void init() {
    }
}
