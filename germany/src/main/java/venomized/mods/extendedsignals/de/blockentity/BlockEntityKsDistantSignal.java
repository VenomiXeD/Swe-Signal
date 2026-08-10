package venomized.mods.extendedsignals.de.blockentity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.blockentity.VariantOption;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.client.GermanyModels;
import venomized.mods.extendedsignals.de.signalling.KsDistantSignalAspect;

import java.util.List;

public class BlockEntityKsDistantSignal extends BlockEntityKs<IDistantSignalAspect> {
    public BlockEntityKsDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected boolean withDistantScreen() {
        return true;
    }


    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting()
                .withLight("braking_distance", SignalLight.whiteLight(3.5d / 16d, 93 / 16f, -8.4 / 16d, 1.75f, 1.75f, 0f))
                .withLight("proceed", SignalLight.greenLight(2.5d / 16d, 87.75d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0f))
                .withLight("danger", SignalLight.yellowLight(-2.5d / 16d, 87.75 / 16f, -8.55 / 16d, 2.75f, 2.75f, 0f))
                .withFadeTicks(2);
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return KsDistantSignalAspect.interpret(state, incomingDirection);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variantData = super.constructVariantData();
        variantData.addVariantOption(new VariantOption("400_left", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.400.left"), () -> GermanyModels.KS_VR_400_LEFT));
        variantData.addVariantOption(new VariantOption("400_right", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.400.right"), () -> GermanyModels.KS_VR_400_RIGHT));
        variantData.addVariantOption(new VariantOption("1000_left", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.1000.left"), () -> GermanyModels.KS_VR_1000_LEFT));
        variantData.addVariantOption(new VariantOption("1000_right", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.1000.right"), () -> GermanyModels.KS_VR_1000_RIGHT));
        return variantData;
    }
}
