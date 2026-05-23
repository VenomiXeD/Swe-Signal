package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;


public abstract class BlockEntityMainSignal extends BlockEntitySignal<IMainSignalAspect> {
    public BlockEntityMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }
}
