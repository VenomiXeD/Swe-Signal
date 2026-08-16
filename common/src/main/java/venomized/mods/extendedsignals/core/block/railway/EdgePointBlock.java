package venomized.mods.extendedsignals.core.block.railway;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import venomized.mods.extendedsignals.core.block.ExtendedSignalsBlock;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPointModifier;
import venomized.mods.extendedsignals.core.menu.CoreMenus;
import venomized.mods.extendedsignals.core.menu.MenuModifierPoint;

public abstract class EdgePointBlock<T extends BlockEntity> extends ExtendedSignalsBlock implements IBE<T> {
    /**
     * @param pProperties
     */
    public EdgePointBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
    }

    /**
     * @param state
     * @param level
     * @param pos
     * @param player
     * @param hitResult
     * @return
     */
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        getBlockEntityOptional(level, pos).ifPresent(e -> {
            if (e instanceof BlockEntityPointModifier<?> be)
                CoreMenus.MODIFIER_POINT.open(
                        (ServerPlayer) player, Component.empty(),
                        (id, inv, p) -> new MenuModifierPoint(CoreMenus.MODIFIER_POINT.get(), id, inv, be),
                        buf -> buf.writeBlockPos(pos)
                );
        });
        return InteractionResult.CONSUME;
    }
}
