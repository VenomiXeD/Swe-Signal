package venomized.mods.extendedsignals.nl.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.railway.BlockCrossingObject;
import venomized.mods.extendedsignals.nl.blockentity.BlockEntityGate;
import venomized.mods.extendedsignals.nl.blockentity.NetherlandBlockEntities;

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
        return NetherlandBlockEntities.GATE.create(pos, state);
    }

    /**
     * @param level
     * @param state
     * @param blockEntityType
     * @param <T>
     * @return
     */
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide && blockEntityType.isValid(state)) {
            return BlockEntityGate::serverTick;
        }

        return null;
    }
}
