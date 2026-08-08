package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.signalling.HvMainSignalAspect;

public class BlockEntityHVMainSignal extends BlockEntitySignal<IMainSignalAspect> {
    public BlockEntityHVMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return HvMainSignalAspect.interpret(state, direction);
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
                .withLight("hp_reduction", SignalLight.yellowLight(2.75d / 16d, 96.25 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f));
    }
}
