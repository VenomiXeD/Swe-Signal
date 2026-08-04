package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.signaling.MainDwarfSignalAspect;

public class BlockEntityMainDwarfSignal extends BlockEntitySignal<MainDwarfSignalAspect> {
    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull MainDwarfSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return new MainDwarfSignalAspect(state);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(0.411157d, 1.36563d, -0.185d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(-0.16966d, 1.20938d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0.118182d, 1.08029d, -0.175d, 3.25f, 3.25f, 0f),

                new SignalLightPlacement(0.186197d, 0.773161d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(-0.18611d, 0.773161d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0.186197, 0.466614d, -0.175d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(-0.18611d, 0.466614d, -0.175d, 3.25f, 3.25f, 0f)
        };
    }
}
