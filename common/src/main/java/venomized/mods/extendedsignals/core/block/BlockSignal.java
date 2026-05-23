package venomized.mods.extendedsignals.core.block;

import com.tterrag.registrate.util.nullness.NonNullFunction;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.ExtendedSignalsCoreBlockEntities;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * General purpose, generic signal block intended to cover most basic signals
 */
public class BlockSignal extends ExtendedSignalBlock implements EntityBlock {
    private final SignalType signalType;

    public enum SignalType {
        MAIN,
        DISTANT,
        COMBINED
    }

    @Getter
    private final int signalLightCount;

    /**
     * @param pProperties
     */
    private BlockSignal(Properties pProperties, int signalLightCount, SignalType signalType) {
        super(pProperties);
        this.signalLightCount = signalLightCount;
        this.signalType = signalType;
    }

    public static NonNullFunction<Properties, BlockSignal> generic(int lightCount, SignalType signalType) {
        return (properties) -> new BlockSignal(
                properties, lightCount, signalType
        );
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return switch (signalType) {
            case MAIN -> ExtendedSignalsCoreBlockEntities.MAIN_SIGNAL.create(pPos, pState);
            case DISTANT -> throw new UnsupportedOperationException("Not implemented yet");
            case COMBINED -> ExtendedSignalsCoreBlockEntities.COMBINED_SIGNAL.create(pPos, pState);
            default -> throw new UnsupportedOperationException("Not implemented yet");
        };
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

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @param pState
     * @deprecated call via {@link BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
