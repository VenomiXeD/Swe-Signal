package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityNe5 extends ModelBlockEntity {
    public BlockEntityNe5(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData data = super.constructVariantData();
        data.addVariantOptionLeft(new VariantData.VariantOption("west_right", Component.translatable("screens.extended_signals_de.modelconfig.variant.west_right"), () -> GermanyModels.Ne5Models.WEST_RIGHT));
        data.addVariantOptionLeft(new VariantData.VariantOption("west_both", Component.translatable("screens.extended_signals_de.modelconfig.variant.west_both"), () -> GermanyModels.Ne5Models.WEST_BOTH));
        data.addVariantOptionLeft(new VariantData.VariantOption("west_left", Component.translatable("screens.extended_signals_de.modelconfig.variant.west_left"), () -> GermanyModels.Ne5Models.WEST_LEFT));
        data.addVariantOptionRight(new VariantData.VariantOption("east", Component.translatable("screens.extended_signals_de.modelconfig.variant.east"), () -> GermanyModels.Ne5Models.EAST));
        data.addVariantOptionRight(new VariantData.VariantOption("east_left", Component.translatable("screens.extended_signals_de.modelconfig.variant.east_left"), () -> GermanyModels.Ne5Models.EAST_LEFT));
        data.addVariantOptionRight(new VariantData.VariantOption("east_both", Component.translatable("screens.extended_signals_de.modelconfig.variant.east_both"), () -> GermanyModels.Ne5Models.EAST_BOTH));
        data.addVariantOptionRight(new VariantData.VariantOption("east_right", Component.translatable("screens.extended_signals_de.modelconfig.variant.east_right"), () -> GermanyModels.Ne5Models.EAST_RIGHT));

        return data;
    }
}
