package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.blockentity.ExtendedSignalsCoreBlockEntity;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityMainDwarfSignal;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

public class BlockModernMainDwarfSignal extends BlockModernDwarfSignal {

    public BlockModernMainDwarfSignal(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ExtendedSignalsSwedenBlockEntities.BE_MAIN_DWARF_SIGNAL.get().create(pPos, pState);
    }
}
