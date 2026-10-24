package venomized.mods.extendedsignals.de.blockentity.hvk;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.client.GermanyModels;
import venomized.mods.extendedsignals.de.signalling.HvCombinedSignalAspectCompositor;

public class BlockEntityHVKBlockCombinedSignal extends BlockEntityHVKSignal<ICombinedSignalAspect> {
    public BlockEntityHVKBlockCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
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
        return true;
    }

    /**
     * @return
     */
    @Override
    public PartialModel getSign() {
        return variantData().getSelectedVariant() == 2 ? GermanyModels.HVKModels.SIGN_MAIN : GermanyModels.HVKModels.SIGN_MAIN_OFFSET;
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("hp_proceed", new SignalLight(2.25d / 16d, 115.3d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.GREEN))
                .withLight("hp_stop_left", new SignalLight(2.25d / 16d, 111.6d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.RED))
                // .withLight("hp_stop_right", new SignalLight(-2.25d/16d, 111.6d/16d, -8.55d/16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.RED))
                .withLight("hp_reduction", new SignalLight(2.25d / 16d, 102.1d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.YELLOW))

                .withLight("vr_braking_distance", new SignalLight(3d / 16d, 93d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0f, ISignalAspect.RGB.WHITE))
                .withLight("vr_green_right", new SignalLight(-2.75d / 16d, 93.1d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.GREEN))
                .withLight("vr_yellow_right", new SignalLight(-2.75d / 16d, 89.6d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.YELLOW))
                .withLight("vr_green_left", new SignalLight(2.75d / 16d, 86.3d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.GREEN))
                .withLight("vr_yellow_left", new SignalLight(2.75d / 16d, 82.85d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.YELLOW))

                .withLight("unused0", new SignalLight(-2d / 16d, 108.25d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused1", new SignalLight(0d / 16d, 105.5d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused2", new SignalLight(-3.5d / 16d, 105.5d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE));
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return new HvCombinedSignalAspectCompositor(state, incomingDirection);
    }
}
