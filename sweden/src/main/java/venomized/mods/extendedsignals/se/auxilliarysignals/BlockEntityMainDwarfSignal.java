package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IMainDwarfSignalAspect;

public class BlockEntityMainDwarfSignal extends BlockEntitySignal<IMainDwarfSignalAspect> {
    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[0];
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public IMainDwarfSignalAspect interpret(RawSignalState rawState) {
        return null;
    }
}
