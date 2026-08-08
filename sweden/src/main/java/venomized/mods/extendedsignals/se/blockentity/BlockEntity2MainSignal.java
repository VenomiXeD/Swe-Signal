package venomized.mods.extendedsignals.se.blockentity;

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
import venomized.mods.extendedsignals.se.signaling.MainSignalAspect;

public class BlockEntity2MainSignal extends BlockEntitySignal<IMainSignalAspect> {
    public BlockEntity2MainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting()
                .withLight("l0", new SignalLight(0d, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0f).withDefaultColor(0, 255, 0))
                .withLight("l1", new SignalLight(0d, 19.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0f).withDefaultColor(255, 0, 0));
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return MainSignalAspect.interpret(state, incomingDirection);
    }
}
