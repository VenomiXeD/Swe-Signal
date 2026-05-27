package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public class BlockEntityEndpointSignal extends BlockEntitySignal<ISignalAspect> {
    public BlockEntityEndpointSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[] {
                new SignalLightPlacement(0d, 1.17188d,0.005d, 3.25f, 3.25f,0f)
        };
    }

    /**
     * @param state
     * @return
     */
    @Override
    public @NotNull ISignalAspect interpret(SignalStateNode state) {
        return (a, b) -> {
        };
    }
}
