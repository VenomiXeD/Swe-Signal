package venomized.mods.extendedsignals.se.blockentity.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.signaling.DwarfSignalAspect;

public class BlockEntityDwarfSignal extends BlockEntitySignal<DwarfSignalAspect> {
    public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting().withLight(
                        "l0", new SignalLight(-0.18611, 0.896875d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))
                .withLight(
                        "l1", new SignalLight(0.1d, 0.796875d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))
                .withLight(
                        "l2", new SignalLight(0.186197d, 0.466614d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255))
                .withLight(
                        "l3", new SignalLight(-0.18611, 0.466614d, -0.175d, 3.25f, 3.25f, 0f).withDefaultColor(255, 255, 255));
    }

    /**
     * @param state
     * @param side
     * @return
     */
    @Override
    public @NotNull DwarfSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return DwarfSignalAspect.interpret(state, incomingDirection);
    }
}
