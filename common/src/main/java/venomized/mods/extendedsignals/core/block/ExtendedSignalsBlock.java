package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.CoreMenus;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;
import venomized.mods.extendedsignals.core.menu.MenuModelConfig;

public abstract class ExtendedSignalsBlock extends Block {

    /**
     * @param pProperties
     */
    public ExtendedSignalsBlock(Properties pProperties) {
        super(pProperties);
    }

    private static float getSnappedRotation(BlockPos blockPos, Vec3 targetPos) {
        double dx = targetPos.x - (blockPos.getX() + 0.5);
        double dz = targetPos.z - (blockPos.getZ() + 0.5);

        double angle = Math.toDegrees(Math.atan2(dz, dx));
        angle = angle + 90.0;
        angle = (angle + 360.0) % 360.0;
        angle = Math.round(angle / 22.5) * 22.5;

        return (float) angle;
    }

    /**
     * @param pLevel
     * @param pPos
     * @param pState
     * @param pPlacer
     * @param pStack
     */
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pPlacer == null)
            super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        if (pLevel.getBlockEntity(pPos) instanceof IConfigurableModelBlockEntity rotateableBlockEntity) {
            rotateableBlockEntity.setYOrientation(
                    getSnappedRotation(pPos, pPlacer.position())
            );
            if (pLevel.getBlockEntity(pPos) instanceof CoreBlockEntity coreBlockEntity) {
                coreBlockEntity.sync();
            }
        }
    }

    /**
     * @param stack
     * @param state
     * @param level
     * @param pos
     * @param player
     * @param hand
     * @param hitResult
     * @return
     */
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof IConfigurableModelBlockEntity configurableModelBlockEntity && configurableModelBlockEntity.supportsConfiguration()) {
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(
                        new SimpleMenuProvider(
                                ((pContainerId, pPlayerInventory, pPlayer1) -> new MenuModelConfig(
                                        CoreMenus.MODEL_CONFIG.get(),
                                        pContainerId,
                                        pPlayerInventory,
                                        pos
                                )),
                                Component.literal("TEST")
                        ), pos
                );
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    // /**
    //  * @param pState
    //  * @param pLevel
    //  * @param pPos
    //  * @param pPlayer
    //  * @param pHand
    //  * @param pHit
    //  * @return
    //  */
    // @Override
    // public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
    //     if (pLevel.isClientSide())
    //         return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
//
    //     if (!pPlayer.getItemInHand(pHand).is(AllTags.AllItemTags.WRENCH.tag))
    //         return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
//
    //     if (!(pLevel.getBlockEntity(pPos) instanceof ITranslatableBlockEntity translatableBlock))
    //         return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
//
//
//
    //     return InteractionResult.SUCCESS;
    // }
}
