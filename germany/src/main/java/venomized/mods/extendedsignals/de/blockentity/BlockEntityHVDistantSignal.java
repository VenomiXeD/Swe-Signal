package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.signalling.HvDistantSignalAspect;

public class BlockEntityHVDistantSignal extends BlockEntitySignal<IDistantSignalAspect> {
    public BlockEntityHVDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public @NotNull IDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        SignalStateNode distant = state.getNextState();
        if (distant == null || distant.isStop())
            return HvDistantSignalAspect.EXPECT_STOP;

        return HvDistantSignalAspect.interpret(state, direction);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(5.25d / 16d, 99.5d / 16d, -6.5d / 16d, 1.5f, 1.5f, 0.5f),

                new SignalLightPlacement(-2.25 / 16d, 99d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
                new SignalLightPlacement(-6.25 / 16d, 99d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),

                new SignalLightPlacement(6 / 16d, 89.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
                new SignalLightPlacement(2 / 16d, 89.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
        };
    }
}
