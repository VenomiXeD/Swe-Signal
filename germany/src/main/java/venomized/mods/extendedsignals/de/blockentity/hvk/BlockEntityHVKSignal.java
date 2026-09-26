package venomized.mods.extendedsignals.de.blockentity.hvk;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityGermanySignal;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public abstract class BlockEntityHVKSignal<T extends ISignalAspect> extends BlockEntityGermanySignal<T> {
    public BlockEntityHVKSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    protected abstract boolean distantSignal();

    protected abstract boolean mainSignal();

    /**
     * @return
     */
    @Override
    public float getZs3MatrixDisplaySpeed() {
        float speed = super.getZs3MatrixDisplaySpeed();
        return speed == 4 ? -1 : speed;
    }

    /**
     * @return
     */
    @Override
    public float getZs3vMatrixDisplaySpeed() {
        float speed = super.getZs3vMatrixDisplaySpeed();
        return speed == 4 ? -1 : speed;
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variants = new VariantData().setDisplayBlockModel(true);
        variants.addVariantOption(new VariantData.VariantOption("default", Component.translatable("screens.extended_signals.modelconfig.variants.default"), () -> GermanyModels.HVKModels.MAST_CENTER));
        variants.addVariantOptionLeft(new VariantData.VariantOption("400_left", Component.translatable("screens.extended_signals_de.modelconfig.offset.400.left"), () -> GermanyModels.HVKModels.LEFT_400));
        variants.addVariantOptionRight(new VariantData.VariantOption("400_right", Component.translatable("screens.extended_signals_de.modelconfig.offset.400.right"), () -> GermanyModels.HVKModels.RIGHT_400));
        variants.addVariantOptionLeft(new VariantData.VariantOption("1000_left", Component.translatable("screens.extended_signals_de.modelconfig.offset.1000.left"), () -> GermanyModels.HVKModels.LEFT_1000));
        variants.addVariantOptionRight(new VariantData.VariantOption("1000_right", Component.translatable("screens.extended_signals_de.modelconfig.offset.1000.right"), () -> GermanyModels.HVKModels.RIGHT_1000));

        if (mainSignal()) {
            variants.addCheckboxOption(new VariantData.VariantOption("zs3_matrix", Component.translatable("screens.extended_signals_de.modelconfig.screens.zs3_matrix"), () -> null));
            variants.addCheckboxOption(new VariantData.VariantOption("zs3_metal", Component.translatable("screens.extended_signals_de.modelconfig.screens.zs3_metal"), () -> null));

            variants.addTextBoxOption(new VariantData.TextBoxOption("zs3_value", Component.translatable("screens.extended_signals_de.modelconfig.screens.textbox.zs3.label"), Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.textbox.zs3.tooltip")));
        }
        if (distantSignal()) {
            variants.addCheckboxOption(new VariantData.VariantOption("zs3v_matrix", Component.translatable("screens.extended_signals_de.modelconfig.screens.zs3v_matrix"), () -> GermanyModels.HVKModels.ZS3V_MATRIX));
            variants.addCheckboxOption(new VariantData.VariantOption("zs3v_metal", Component.translatable("screens.extended_signals_de.modelconfig.screens.zs3v_metal"), () -> GermanyModels.HVKModels.ZS3V_METAL));

            variants.addTextBoxOption(new VariantData.TextBoxOption("zs3v_value", Component.translatable("screens.extended_signals_de.modelconfig.screens.textbox.zs3v.label"), Component.translatable("screens.extended_signals_de.modelconfig.screens.ks.textbox.zs3v.tooltip")));
        }
        return variants;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract PartialModel getSign();

    /**
     * @return
     */
    @Override
    public boolean hasMainSignalCapability() {
        return mainSignal();
    }
}
