package venomized.mods.extendedsignals.de.blockentity.hvk;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.client.GermanyModels;
import venomized.mods.extendedsignals.de.signalling.HvDistantSignalAspect;

public class BlockEntityHVKDistantSignal extends BlockEntityHVKSignal<IDistantSignalAspect> {
    public BlockEntityHVKDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected boolean distantSignal() {
        return true;
    }

    /**
     * @return
     */
    @Override
    protected boolean mainSignal() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public PartialModel getSign() {
        return variantData().getSelectedVariant() == 2 ? GermanyModels.HVKModels.SIGN_DISTANT : GermanyModels.HVKModels.SIGN_DISTANT_OFFSET;
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("vr_braking_distance", new SignalLight(3d / 16d, 93d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0f, ISignalAspect.RGB.WHITE))
                .withLight("vr_green_right", new SignalLight(-2.75d / 16d, 93.1d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.GREEN))
                .withLight("vr_yellow_right", new SignalLight(-2.75d / 16d, 89.6d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.YELLOW))
                .withLight("vr_green_left", new SignalLight(2.75d / 16d, 86.3d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.GREEN))
                .withLight("vr_yellow_left", new SignalLight(2.75d / 16d, 82.85d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.YELLOW));
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (state.getNextState() == null || state.getNextState().isStop())
            return HvDistantSignalAspect.EXPECT_STOP;
        return HvDistantSignalAspect.interpret(state, incomingDirection);
    }
}
