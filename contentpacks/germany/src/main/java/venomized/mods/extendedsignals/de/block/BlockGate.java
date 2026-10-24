package venomized.mods.extendedsignals.de.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.railway.BlockCrossingObject;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityGate;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

public class BlockGate extends BlockCrossingObject<BlockEntityGate> {
    public BlockGate(Properties properties) {
        super(properties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return GermanyBlockEntities.CROSSING_GATE.get().create(pos, state);
    }
}
