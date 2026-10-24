package venomized.mods.extendedsignals.hu.blockentity.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityModelled;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.hu.client.HungaryModels;

public class BlockEntityDistantSignalIndicator extends BlockEntityModelled {
    public BlockEntityDistantSignalIndicator(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variants = super.constructVariantData();
        variants.addVariantOption(new VariantData.VariantOption("b", Component.translatable("screens.extended_signals_hu.modelconfig.variant.distant_type_b"), () -> HungaryModels.DISTANT_SIGNAL_INDICATOR_TYPE_B));

        return variants;
    }
}
