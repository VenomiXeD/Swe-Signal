package venomized.mods.extendedsignals.nl.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityModelled;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.nl.client.NetherlandModels;

public class BlockEntityCrossingSign extends BlockEntityModelled {
    public BlockEntityCrossingSign(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData(VariantData variantData) {
        VariantData variants = super.constructVariantData(variantData);
        variants.addVariantOption(new VariantData.VariantOption("variant1", Component.translatable("screens.extended_signals_nl.modelconfig.crossing_sign.variant.let_op_trein"), () -> NetherlandModels.CROSSING_SIGN_LET_OP_TREIN));
        return variants;
    }
}
