package venomized.mods.extendedsignals.core.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;

/**
 * General purpose, generic signal block intended to cover most basic signals
 */
public abstract class BlockSignal extends BlockModelled implements EntityBlock {
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
        if (pLevel.isClientSide()) {
            return (level, pos, state, be) -> {
                if (be instanceof BlockEntitySignal<?> signal) {
                    BlockEntitySignal.clientTick(signal, pLevel, pos, state);
                }
            };
        } else {
            return (level, pos, state, be) -> {
                if (be instanceof BlockEntitySignal<?> signal) {
                    BlockEntitySignal.serverTick(signal, level, pos, state);
                }
            };
        }
    }
}
