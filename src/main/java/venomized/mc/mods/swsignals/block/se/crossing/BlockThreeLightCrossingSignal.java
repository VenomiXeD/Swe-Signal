package venomized.mc.mods.swsignals.block.se.crossing;

import com.simibubi.create.AllTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.BlockRailroadCrossingObject;
import venomized.mc.mods.swsignals.block.se.BlockSignal;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;

public class BlockThreeLightCrossingSignal extends BlockRailroadCrossingObject {
    public BlockThreeLightCrossingSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SeBlockEntities.BE_THREE_LIGHT_CROSSING_LIGHT_SIGNAL.create(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder
                .add(BlockSignal.MOUNTED);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack itemInHand = pPlayer.getItemInHand(pHand);
            if (itemInHand.is(AllTags.AllItemTags.WRENCH.tag)) {
                pLevel.setBlock(pPos, pState.setValue(BlockSignal.MOUNTED, !pState.getValue(BlockSignal.MOUNTED)), 2);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
