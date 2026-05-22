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
        super(t, pPos, pBlockState);
    }
}
