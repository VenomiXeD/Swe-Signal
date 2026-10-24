package venomized.mods.extendedsignals.hu.blockentity.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

public abstract class BlockEntityTwoLightSignal<T extends ISignalAspect> extends BlockEntitySignal<T> {
    public BlockEntityTwoLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("l0", new SignalLight(0, 18.75d / 16d, -1.5015d / 16d, 4.75f, 4.75f, 0, ISignalAspect.RGB.RED))
                .withLight("l1", new SignalLight(0, 9d / 16d, -1.5015d / 16d, 4.75f, 4.75f, 0, ISignalAspect.RGB.GREEN));
    }
}
