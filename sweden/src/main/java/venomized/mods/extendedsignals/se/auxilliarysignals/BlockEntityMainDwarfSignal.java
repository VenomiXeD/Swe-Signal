package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IMainDwarfSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public class BlockEntityMainDwarfSignal extends BlockEntitySignal<IMainDwarfSignalAspect> {
    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param state
     * @return
     */
    @Override
    public @NotNull IMainDwarfSignalAspect interpret(RawSignalState state) {
        return (A, B) -> {
        };
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[] {
                new SignalLightPlacement(-0.411157d,1.36563d,-0.185d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0.16966d,1.20938d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(-0.118182d, 1.08029d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0.18611,0.773161d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(-0.186197d, 0.773161d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0.18611,0.466614d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(-0.186197d, 0.466614d, -0.175d, 3.25f, 3.25f, 0f)
        };
    }
}
