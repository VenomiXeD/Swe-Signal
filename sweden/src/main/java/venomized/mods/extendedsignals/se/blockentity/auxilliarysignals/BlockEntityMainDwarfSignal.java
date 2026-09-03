package venomized.mods.extendedsignals.se.blockentity.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.blockentity.BlockEntitySwedishSignal;
import venomized.mods.extendedsignals.se.signaling.MainDwarfSignalAspect;

public class BlockEntityMainDwarfSignal extends BlockEntitySwedishSignal<MainDwarfSignalAspect> {
    public BlockEntityMainDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     *
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("stop", new SignalLight(0.411157d, 1.36563d, -0.185d, 3.25f, 3.25f, 0f).withDefaultColor(255, 0, 0))

                .withLight("l0", new SignalLight(-0.16966d, 1.20938d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))
                .withLight("l1", new SignalLight(0.118182d, 1.08029d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))
                .withLight("l2", new SignalLight(0.186197d, 0.773161d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))
                .withLight("l3", new SignalLight(-0.18611d, 0.773161d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))

                .withLight("green_left", new SignalLight(0.186197, 0.466614d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(0, 255, 0))
                .withLight("green_right", new SignalLight(-0.18611d, 0.466614d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(0, 255, 0));
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
}
