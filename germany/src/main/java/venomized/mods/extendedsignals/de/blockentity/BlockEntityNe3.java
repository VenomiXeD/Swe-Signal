package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityNe3 extends ModelBlockEntity {
    public BlockEntityNe3(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public VariantData constructVariantData() {
        VariantData variantData = super.constructVariantData();
        variantData.addVariantOptionRight(new VariantData.VariantOption("2", Component.translatable("screens.extended_signals_de.modelconfig.variant.ne3.2"), () -> GermanyModels.NE_3_2));
        variantData.addVariantOptionRight(new VariantData.VariantOption("3", Component.translatable("screens.extended_signals_de.modelconfig.variant.ne3.3"), () -> GermanyModels.NE_3_3));

        return variantData;
    }
}
