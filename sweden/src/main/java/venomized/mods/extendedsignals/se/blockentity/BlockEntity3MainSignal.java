package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.signalling.MainSignalAspect;

public class BlockEntity3MainSignal extends BlockEntitySignal<IMainSignalAspect> {
    public BlockEntity3MainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(0d, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0d, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0f),
                new SignalLightPlacement(0d, 19.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0f)
        };
    }

    /**
     * @param state
     * @param direction
     * @return
     */
    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return MainSignalAspect.interpret(state, direction);
    }
}
