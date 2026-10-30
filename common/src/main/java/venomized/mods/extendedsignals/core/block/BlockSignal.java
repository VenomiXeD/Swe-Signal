package venomized.mods.extendedsignals.core.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;

/**
 * General purpose, generic signal block intended to cover most basic signals
 */
public class BlockSignal extends BlockModelled {
    /**
     * @param pProperties
     */
    public BlockSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pLevel
     * @param pState
     * @param pBlockEntityType
     * @param <T>
     * @return
     */
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide() && pBlockEntityType.isValid(pState)) {
            return (level, pos, state, be) -> {
                BlockEntitySignal.clientTick((BlockEntitySignal<?>) be, pLevel, pos, state);
            };
        }
        return super.getTicker(pLevel, pState, pBlockEntityType);
    }
}
