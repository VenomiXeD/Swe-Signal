package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IDwarfSignalAspect;

public class BlockEntityDwarfSignal extends BlockEntitySignal<IDwarfSignalAspect> {
    public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }


    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{};
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public IDwarfSignalAspect interpret(RawSignalState rawState) {
        return null;
    }
}
