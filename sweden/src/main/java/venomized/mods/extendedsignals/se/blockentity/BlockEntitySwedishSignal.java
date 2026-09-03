package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

public abstract class BlockEntitySwedishSignal<T extends ISignalAspect> extends BlockEntitySignal<T> {
    public BlockEntitySwedishSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
        getSignalContainer()
                .withFadeMilliSeconds(375);
    }
}
