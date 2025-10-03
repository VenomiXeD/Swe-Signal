package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.Sw45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignalBox;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;
import venomized.mc.mods.swsignals.client.ui.MenuTest;

public class BlockSignalBox extends Sw45DegreeBlock implements EntityBlock {
    public BlockSignalBox(Properties pProperties) {
        super(pProperties.noOcclusion().noCollission());
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SeBlockEntities.BE_SE_SIGNAL_BOX.create(pPos, pState);
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
        if (pBlockEntityType == SeBlockEntities.BE_SE_SIGNAL_BOX.get()) {
            return ((pLevel1, pPos, pState1, pBlockEntity) -> ((BlockEntitySignalBox) pBlockEntity).tick(pLevel1, pPos, pState1, (BlockEntitySignalBox) pBlockEntity));
        }
        return null;
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pPlayer
     * @param pHand
     * @param pHit
     * @deprecated
     */
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (pPlayer.getItemInHand(pHand).isEmpty()) {
            BlockEntitySignalBox blockEntity = (BlockEntitySignalBox) pLevel.getBlockEntity(pPos);
            NetworkHooks.openScreen((ServerPlayer) pPlayer, pState.getMenuProvider(pLevel, pPos), (data) -> {
                data.writeNbt(blockEntity.getUpdateTag());
            });
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @deprecated
     */
    @Override
    public @Nullable MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((pId, pInventory, pPlayer) -> new MenuTest(pId, pInventory, ContainerLevelAccess.create(pLevel, pPos)), Component.translatable("menu.title.swsignal.mymenu"));
    }
}
