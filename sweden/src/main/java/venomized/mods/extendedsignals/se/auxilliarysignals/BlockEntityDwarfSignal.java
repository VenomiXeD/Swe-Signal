package venomized.mods.extendedsignals.se.auxilliarysignals;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.se.SwedishSignalAspect;
import venomized.mods.extendedsignals.se.blockentity.mainsignals.BlockEntitySignal;
import venomized.mods.extendedsignals.util.SignalUtilities;

public class BlockEntityDwarfSignal extends BlockEntitySignal {
    public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        this(t, pPos, pBlockState, 4);
    }

    public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState, int lightCount) {
        super(t, pPos, pBlockState, lightCount);
    }

    @Override
    public void computeSignalLightValues(SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
        switch (aspect) {
            default -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, false);
                SignalUtilities.computeLightValueAt(1, lightLevels, true);
                SignalUtilities.computeLightValueAt(2, lightLevels, true);
                SignalUtilities.computeLightValueAt(3, lightLevels, false);
            }
            case STOP -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, false);
                SignalUtilities.computeLightValueAt(1, lightLevels, false);
                SignalUtilities.computeLightValueAt(2, lightLevels, true);
                SignalUtilities.computeLightValueAt(3, lightLevels, true);
            }
            case PROCEED_80 -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, false);
                SignalUtilities.computeLightValueAt(1, lightLevels, true);
                SignalUtilities.computeLightValueAt(2, lightLevels, false);
                SignalUtilities.computeLightValueAt(3, lightLevels, true);
            }
            // case YELLOW -> {
            //     SignalUtilities.computeLightValueAt(0, lightLevels, true);
            //     SignalUtilities.computeLightValueAt(1, lightLevels, false);
            //     SignalUtilities.computeLightValueAt(2, lightLevels, false);
            //     SignalUtilities.computeLightValueAt(3, lightLevels, true);
            // }
        }
    }
}
