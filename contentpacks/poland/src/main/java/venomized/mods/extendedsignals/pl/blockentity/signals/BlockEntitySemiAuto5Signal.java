package venomized.mods.extendedsignals.pl.blockentity.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.LightContainer;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.pl.signalling.SemiAuto5Aspect;

public class BlockEntitySemiAuto5Signal extends BlockEntityPolandSignal<IMainSignalAspect> {
    public BlockEntitySemiAuto5Signal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public boolean hasMainSignalCapability() {
        return true;
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(LightContainer signalLights) {
        final double z = -1.715d;
        final float d = 2.95f;
        signalLights
                .withLight("l0", SignalLight.greenLightPx(0d, 95.95d, z, d, d, 0f))
                .withLight("l1", SignalLight.yellowLightPx(0d, 90.35d, z, d, d, 0f))
                .withLight("l2", SignalLight.redLightPx(0d, 84.75d, z, d, d, 0f))
                .withLight("l3", SignalLight.yellowLightPx(0d, 79.15d, z, d, d, 0f))
                .withLight("l4", SignalLight.whiteLightPx(0d, 73.55d, z, d, d, 0f));
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return SemiAuto5Aspect.interpret(state);
    }
}
