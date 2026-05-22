package venomized.mods.extendedsignals.se.auxilliarysignals;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.se.SwedishSignalAspect;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;
import venomized.mods.extendedsignals.util.SignalUtilities;

public class BlockEntityMainDwarfSignal extends BlockEntityDwarfSignal {
    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }
}
