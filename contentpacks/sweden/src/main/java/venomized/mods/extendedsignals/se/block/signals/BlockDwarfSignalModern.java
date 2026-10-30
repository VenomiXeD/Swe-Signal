package venomized.mods.extendedsignals.se.block.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.block.BlockSwedenSignal;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockDwarfSignalModern extends BlockSwedenSignal implements EntityBlock {
    public BlockDwarfSignalModern(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.DWARF_SIGNAL.create(pPos, pState);
    }
}
