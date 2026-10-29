package venomized.mods.extendedsignals.hu.blockentity.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.signalling.IDwarfSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.hu.signalling.ShuntSignal;

public class BlockEntityShuntSignal extends BlockEntitySignal<ISignalAspect> {
    public BlockEntityShuntSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("white", SignalLight.whiteLight(0, 18.75f / 16f, -1.5d / 16d, 4.75f, 4.75f, 0))
                .withLight("blue", SignalLight.blueLight(0, 9f / 16f, -1.5d / 16d, 4.75f, 4.75f, 0));
    }

    /**
     * @return
     */
    @Override
    public boolean hasMainSignalCapability() {
        return false;
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull ISignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return ShuntSignal.interpret(state);
    }
}
