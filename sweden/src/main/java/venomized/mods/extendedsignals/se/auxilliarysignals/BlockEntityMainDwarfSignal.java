package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.IMainDwarfSignalAspect;

public class BlockEntityMainDwarfSignal extends BlockEntitySignal<IMainDwarfSignalAspect> {
    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalBlockEntity
     * @return
     */
    @Override
    public IMainDwarfSignalAspect interpret(@Nullable BlockEntitySignal<?> signalBlockEntity) {
        return null;
    }
}
