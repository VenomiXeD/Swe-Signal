package venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SignalUtilities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityMainDwarfSignal extends BlockEntityDwarfSignal {
    public BlockEntityMainDwarfSignal(BlockPos pPos, BlockState pBlockState) {
        super(SwBlockEntities.BE_MAIN_DWARF_SIGNAL.get(), pPos, pBlockState, 7);
    }

    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    @Override
    public void stepSignalLighting(float partialTick, SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
        super.stepSignalLighting(partialTick, aspect, createSignalState, doInvalidBlinking);

        // Light at position 5 (index 4) is red light (stop)
        if (createSignalState == SignalBlockEntity.SignalState.RED) {
            SignalUtilities.computeLightValueAt(4, lightLevels, partialTick, true);
            SignalUtilities.computeLightValueAt(5, lightLevels, partialTick, false);
            SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, false);
            return;
        }

        SignalUtilities.computeLightValueAt(4, lightLevels, partialTick, false);

        // Light at position 6 (index 5) is left green light
        if (aspect != null) {
            switch (aspect) {
                case PROCEED_40_SHORT, PROCEED_40_CAUTION:
                    SignalUtilities.computeLightValueAt(5, lightLevels, partialTick, true);
                    SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, false);
                    break;
                case PROCEED_80, PROCEED_80_EXPECT_PROCEED_80:
                    SignalUtilities.computeLightValueAt(5, lightLevels, partialTick, false);
                    SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, true);
                    break;
                case PROCEED_80_EXPECT_STOP:
                    SignalUtilities.computeLightValueAt(5, lightLevels, partialTick, this.blink());
                    SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, false);
                    break;
                case PROCEED_80_EXPECT_PROCEED_40:
                    SignalUtilities.computeLightValueAt(5, lightLevels, partialTick, false);
                    SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, false);
                    break;
                default:
                    break;
            }
        } else {
            if (createSignalState == SignalBlockEntity.SignalState.GREEN) {
                SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, true);
            } else if (createSignalState == SignalBlockEntity.SignalState.YELLOW) {
                SignalUtilities.computeLightValueAt(6, lightLevels, partialTick, this.blink());
            }
        }

    }
}
