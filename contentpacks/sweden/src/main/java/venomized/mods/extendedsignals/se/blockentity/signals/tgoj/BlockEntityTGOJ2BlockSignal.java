package venomized.mods.extendedsignals.se.blockentity.signals.tgoj;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.LightContainer;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.blockentity.BlockEntitySwedishSignal;
import venomized.mods.extendedsignals.se.client.SwedenModels;
import venomized.mods.extendedsignals.se.signaling.tgoj.TGOJBlockSignalAspect;

public class BlockEntityTGOJ2BlockSignal extends BlockEntitySwedishSignal<IMainSignalAspect> {
    public BlockEntityTGOJ2BlockSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalLights
     */
    @Override
    public void configureSignalLights(LightContainer signalLights) {
        signalLights
                .withLight("main_green", SignalLight.greenLight(0, 26.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0))
                .withLight("main_red", SignalLight.redLight(0, 19.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0));
    }

    /**
     * @return
     */
    @Override
    public boolean hasMainSignalCapability() {
        return true;
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData(VariantData variantData) {
        variantData.addVariantOption(new VariantData.VariantOption("pole", Component.translatable("screens.extended_signals_se.modelconfig.option.pole"), () -> SwedenModels.SignalModels.TGOJ_BLOCK_SIGNAL_POLE));

        return variantData;
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return TGOJBlockSignalAspect.interpret(state);
    }
}
