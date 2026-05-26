package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.de.signalling.MainSignalAspect;

public class BlockEntityMainSignal extends BlockEntitySignal<IMainSignalAspect> {
    public BlockEntityMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    @Override
    public @NotNull IMainSignalAspect interpret(RawSignalState state) {
        return MainSignalAspect.interpret(state);
    }
    
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(2.75d / 16d, 114 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),
                new SignalLightPlacement(2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),
                new SignalLightPlacement(-2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),

                new SignalLightPlacement(-2.75d / 16d, 105.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f),
                new SignalLightPlacement(2.75d / 16d, 100.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f),

                new SignalLightPlacement(2.75d / 16d, 96.25 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f)
        };
    }
}
