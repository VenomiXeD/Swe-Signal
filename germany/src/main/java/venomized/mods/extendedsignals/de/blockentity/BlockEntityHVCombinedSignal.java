package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.signalling.HvCombinedSignalAspectCompositor;

public class BlockEntityHVCombinedSignal extends BlockEntitySignal<ICombinedSignalAspect> {
    public BlockEntityHVCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting()
                .withLight("hp_proceed", SignalLight.greenLight(2.75d / 16d, 114 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f))
                .withLight("hp_stop_left", SignalLight.redLight(2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f))
                .withLight("hp_stop_right", SignalLight.redLight(-2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f))
                .withLight("hp_shunt_right", SignalLight.whiteLight(-2.75d / 16d, 105.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f))
                .withLight("hp_shunt_left", SignalLight.whiteLight(2.75d / 16d, 100.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f))
                .withLight("hp_reduction", SignalLight.yellowLight(2.75d / 16d, 96.25 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f))

                .withLight("vr_braking", SignalLight.whiteLight(5.25 / 16d, 78.5d / 16d, -6.65d / 16d, 1.5f, 1.5f, 0.5f))
                .withLight("vr_yellow_right", SignalLight.yellowLight(-2.25 / 16d, 78d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
                .withLight("vr_green_right", SignalLight.greenLight(-6.25 / 16d, 78d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
                .withLight("vr_yellow_left", SignalLight.yellowLight(6 / 16d, 68.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
                .withLight("vr_green_left", SignalLight.greenLight(2 / 16d, 68.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f));
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return new HvCombinedSignalAspectCompositor(state, direction);
    }


}
