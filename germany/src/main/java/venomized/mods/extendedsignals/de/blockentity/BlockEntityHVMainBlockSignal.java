package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.signalling.HvBlockSignalAspect;

public class BlockEntityHVMainBlockSignal extends BlockEntitySignal<HvBlockSignalAspect> {
    public BlockEntityHVMainBlockSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting()
                .withLight("proceed", new SignalLight(2d / 16d, 97d / 16d, -7.65d / 16d, 2.75f, 2.75f, 0.5f, 0, 255, 0))
                .withLight("stop", new SignalLight(-2d / 16d, 97d / 16d, -7.65d / 16d, 2.75f, 2.75f, 0.5f).withDefaultColor(255, 0, 0));
    }
    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull HvBlockSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return HvBlockSignalAspect.interpret(state, incomingDirection);
    }
}
