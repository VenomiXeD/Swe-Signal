package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.ExtendedSignalsBlock;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockModernDwarfSignal extends BlockSwedenSignal implements EntityBlock {
    public BlockModernDwarfSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.DWARF_SIGNAL.create(pPos, pState);
    }
}
