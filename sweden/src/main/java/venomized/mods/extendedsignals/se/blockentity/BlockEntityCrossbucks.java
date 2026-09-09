package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.se.client.SwedenModels;

public class BlockEntityCrossbucks extends ModelBlockEntity {
    public BlockEntityCrossbucks(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public VariantData constructVariantData() {
        VariantData variants = super.constructVariantData();
        variants.addCheckboxOption(new VariantData.VariantOption("2_tracks", Component.translatable("screens.extended_signals_se.modelconfig.crossbuck.2_tracks"), () -> SwedenModels.CROSSBUCK_EXTRA));
        return variants;
    }
}
