package venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;
import venomized.mc.mods.swsignals.rail.SignalUtilities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityDwarfSignal extends BlockEntitySignal {
    public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        this(t, pPos, pBlockState, 4);
    }

    public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState, int lightCount) {
        super(t, pPos, pBlockState, lightCount);
    }

    @Override
    public void computeSignalLightValues(SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
        switch (createSignalState) {
            case INVALID -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, false);
                SignalUtilities.computeLightValueAt(1, lightLevels, true);
                SignalUtilities.computeLightValueAt(2, lightLevels, true);
                SignalUtilities.computeLightValueAt(3, lightLevels, false);
            }
            case RED -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, false);
                SignalUtilities.computeLightValueAt(1, lightLevels, false);
                SignalUtilities.computeLightValueAt(2, lightLevels, true);
                SignalUtilities.computeLightValueAt(3, lightLevels, true);
            }
            case GREEN -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, false);
                SignalUtilities.computeLightValueAt(1, lightLevels, true);
                SignalUtilities.computeLightValueAt(2, lightLevels, false);
                SignalUtilities.computeLightValueAt(3, lightLevels, true);
            }
            case YELLOW -> {
                SignalUtilities.computeLightValueAt(0, lightLevels, true);
                SignalUtilities.computeLightValueAt(1, lightLevels, false);
                SignalUtilities.computeLightValueAt(2, lightLevels, false);
                SignalUtilities.computeLightValueAt(3, lightLevels, true);
            }
        }
    }
}
