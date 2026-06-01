package venomized.mods.extendedsignals.se.block.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockCrossingObject;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityThreeLightCrossingLights;

public class BlockCrossingLights extends BlockCrossingObject<BlockEntityThreeLightCrossingLights> {
    public BlockCrossingLights(Properties properties) {
        super(properties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityThreeLightCrossingLights> getBlockEntityClass() {
        return BlockEntityThreeLightCrossingLights.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityThreeLightCrossingLights> getBlockEntityType() {
        return SwedenBlockEntities.CROSSING_LIGHTS.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SwedenBlockEntities.CROSSING_LIGHTS.create(blockPos, blockState);
    }
}
