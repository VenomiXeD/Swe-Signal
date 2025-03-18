package venomized.mc.mods.swsignals.block.test;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TestBlock extends Block {
    public TestBlock() {
        super(Block.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        System.out.println("PState: " + pState + " PLevel: " + pLevel + " PPos: " + pPos + " PPlayer: " + pPlayer
                + " PHand: " + pHand + " PHit: " + pHit);
        pLevel.setBlock(pPos.above(), Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);

        System.out.println("Is Client: " + pLevel.isClientSide);

        return InteractionResult.SUCCESS;
    }
}