package venomized.mods.extendedsignals.se.blockentity.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public class BlockEntityEndpointSignal extends BlockEntitySignal<ISignalAspect> {
    public BlockEntityEndpointSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting().withLight("l0", new SignalLight(0d, 1.17188d, 0.005d, 3.25f, 3.25f, 0f).withDefaultColor(255, 0, 0));
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull ISignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return (totalTicksForBlockEntity, states) -> {
            if (state.isStop())
                states.powered("l0");
        };
    }
}
