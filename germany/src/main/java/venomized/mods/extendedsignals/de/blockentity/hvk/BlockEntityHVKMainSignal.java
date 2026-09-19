package venomized.mods.extendedsignals.de.blockentity.hvk;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.client.GermanyModels;
import venomized.mods.extendedsignals.de.signalling.HvMainSignalAspect;

public class BlockEntityHVKMainSignal extends BlockEntityHVKSignal<IMainSignalAspect> {
    public BlockEntityHVKMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("hp_proceed", new SignalLight(2.25d / 16d, 96.3d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.GREEN))
                .withLight("hp_stop_left", new SignalLight(2.25d / 16d, 92.6d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.RED))
                .withLight("hp_stop_right", new SignalLight(-2.25d / 16d, 92.6d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.RED))
                .withLight("hp_reduction", new SignalLight(2.25d / 16d, 83.1d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0, ISignalAspect.RGB.YELLOW))

                .withLight("unused0", new SignalLight(2d / 16d, 89.25d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused1", new SignalLight(0d / 16d, 89.25d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused2", new SignalLight(-2d / 16d, 89.25d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused3", new SignalLight(3.5d / 16d, 86.5d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused4", new SignalLight(0 / 16d, 86.5d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE))
                .withLight("unused5", new SignalLight(-3.5d / 16d, 86.5d / 16d, -8.4d / 16d, 1.5f, 1.5f, 0, ISignalAspect.RGB.WHITE));
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return HvMainSignalAspect.interpret(state, incomingDirection);
    }

    /**
     * @return
     */
    @Override
    public PartialModel getSign() {
        return variantData().getSelectedVariant() == 2 ? GermanyModels.HVKModels.SIGN_MAIN : GermanyModels.HVKModels.SIGN_MAIN_OFFSET;
    }

    /**
     * @return
     */
    @Override
    protected boolean distantSignal() {
        return false;
    }

    /**
     * @return
     */
    @Override
    protected boolean mainSignal() {
        return true;
    }
}
