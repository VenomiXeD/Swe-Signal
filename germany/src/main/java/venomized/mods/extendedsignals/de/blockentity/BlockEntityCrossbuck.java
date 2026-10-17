package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityCrossbuck extends ModelBlockEntity {
    public BlockEntityCrossbuck(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variants = super.constructVariantData();
        variants.addVariantOption(new VariantData.VariantOption("electric", Component.translatable("screens.extended_signals_de.modelconfig.variant.crossbuck.electric"), () -> GermanyModels.CROSSBUCK_ELECTRIC));

        return variants;
    }
}
