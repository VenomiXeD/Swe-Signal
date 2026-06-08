package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.signaling.DistantSignalAspect;

public class BlockEntity3DistantSignal extends BlockEntitySignal<ISignalAspect> {
    public BlockEntity3DistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(0d, 30.25d/16d , 0.75d/16d, 3.25f, 3.25f, 0 ),
                new SignalLightPlacement(0d, 24.25d/16d , 0.75d/16d, 3.25f, 3.25f, 0 ),
                new SignalLightPlacement(0d, 18.25d/16d , 0.75d/16d, 3.25f, 3.25f, 0 )
        };
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull ISignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return DistantSignalAspect.interpret(state, direction);
    }
}
