package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.signaling.DistantSignalAspect;

public class BlockEntity3DistantSignal extends BlockEntitySwedishSignal<IDistantSignalAspect> {
    public BlockEntity3DistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     *
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("l0", new SignalLight(0d, 30.25d / 16d, 0.75d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l1", new SignalLight(0d, 24.25d / 16d, 0.75d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 255, 255))
                .withLight("l2", new SignalLight(0d, 18.25d / 16d, 0.75d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0));
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull IDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return DistantSignalAspect.interpret(state, direction);
    }
}
