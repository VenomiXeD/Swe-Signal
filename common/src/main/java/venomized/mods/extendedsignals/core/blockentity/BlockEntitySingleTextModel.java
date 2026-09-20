package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

public abstract class BlockEntitySingleTextModel extends ModelBlockEntity {
    /**
     * @param pType
     * @param pPos
     * @param pBlockState
     */
    public BlockEntitySingleTextModel(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public abstract Vector3f corner0();

    public abstract Vector3f corner1();

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variants = super.constructVariantData();
        variants.addTextBoxOption(new VariantData.TextBoxOption("number", Component.translatable("screens.extended_signals_de.modelconfig.screens.textbox.number.label"), Component.translatable("screens.extended_signals_de.modelconfig.screens.textbox.number.tooltip")));
        return variants;
    }
}
