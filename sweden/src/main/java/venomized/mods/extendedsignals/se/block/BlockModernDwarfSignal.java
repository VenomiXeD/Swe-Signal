package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

public class BlockModernDwarfSignal extends BlockSignal {
    public BlockModernDwarfSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ExtendedSignalsSwedenBlockEntities.DWARF_SIGNAL.create(pPos, pState);
    }
}
