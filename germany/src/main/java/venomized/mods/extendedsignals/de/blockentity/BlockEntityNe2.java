package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityNe2 extends ModelBlockEntity {
    public BlockEntityNe2(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public VariantData constructVariantData() {
        VariantData variantData = super.constructVariantData();
        variantData.addVariantOptionLeft(new VariantData.VariantOption("west_brake", Component.translatable("screens.extended_signals_de.modelconfig.variant.ne_2.west.brake"), () -> GermanyModels.Ne2Models.WEST_REDUCED_BRAKE_DISTANCE));
        variantData.addVariantOptionRight(new VariantData.VariantOption("east_brake", Component.translatable("screens.extended_signals_de.modelconfig.variant.ne_2.east.brake"), () -> GermanyModels.Ne2Models.EAST_REDUCED_BRAKE_DISTANCE));
        return variantData;
    }
}
