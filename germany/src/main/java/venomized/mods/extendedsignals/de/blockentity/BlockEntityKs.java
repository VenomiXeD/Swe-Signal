package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public abstract class BlockEntityKs<T extends ISignalAspect> extends BlockEntitySignal<T> {
    protected boolean withDistantScreen() {
        return false;
    }

    protected boolean withScreen() {
        return false;
    }

    public BlockEntityKs(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variantData = super.constructVariantData();
        if (withScreen()) {
            variantData.addCheckboxOption(new VariantData.VariantOption("matrix", Component.translatable("screens.extended_signals_de.modelconfig.screens.matrix_display"), () -> GermanyModels.KS_MATRIX));
            variantData.addCheckboxOption(new VariantData.VariantOption("zs3_metal", Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.zs3_metal"), () -> GermanyModels.KS_ZS3_METAL));

            variantData.addTextBoxOption(new VariantData.TextBoxOption("zs3_value", Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.textbox.zs3.label"), Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.textbox.zs3.tooltip")));
        }
        if (withDistantScreen()) {
            variantData.addCheckboxOption(new VariantData.VariantOption("matrix_v", Component.translatable("screens.extended_signals_de.modelconfig.screens.matrix_display_v"), () -> GermanyModels.KS_MATRIX_DISTANT));
            variantData.addCheckboxOption(new VariantData.VariantOption("zs3v_metal", Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.zs3v_metal"), () -> GermanyModels.KS_ZS3V_METAL));
            variantData.addTextBoxOption(new VariantData.TextBoxOption("zs3v_value", Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.textbox.zs3v.label"), Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.textbox.zs3v.tooltip")));
        }

        // variantData.addCheckboxOption(new VariantOption("zs2v", Component.translatable("screens.extended_signals_de.modelconfig.screens.zs2v"),() -> null));
        return variantData;
    }

    // /**
    //  * @param tooltip
    //  * @param isPlayerSneaking
    //  * @return
    //  */
    // @Override
    // public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
    //     if (variantData().getCheckboxOptionsTicked().isEmpty() && (getCurrentDisplayedAspect() instanceof IDistantSignalAspect && (getCurrentDisplayedAspect() == KsDistantSignalAspect.EXPECT_REDUCED_SPEED || getCurrentDisplayedAspect() == KsCombinedSignalAspect.EXPECT_REDUCED_SPEED)))
    //         tooltip.addLast(Component.literal("Distant signals require a Zs3 display or a Matrix display for displaying speed reduction aspects."));
    //     return IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    // }
}
