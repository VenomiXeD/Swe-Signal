package venomized.mods.extendedsignals.se.blockentity.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.client.blockentityrenderer.SignalLightPlacement;

public class BlockEntityMainSignal extends BlockEntitySignal {
    private final int lightCount;

    public BlockEntityMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState, int lightCount) {
        super(t, pPos, pBlockState);

        this.lightCount = lightCount;
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] getLights() {
        double baseLightHeight = 19.75 / 16d;

        SignalLightPlacement[] signalLights = new SignalLightPlacement[this.lightCount];
        for (int i = 0; i < this.lightCount; i++) {
            signalLights[i] = new SignalLightPlacement(
                    0, baseLightHeight + (26.75 / 16d - 19.75 / 16d) * i, 0.25d / 16,
                    3, 3, 0
            );
        }

        return signalLights;
    }
}
