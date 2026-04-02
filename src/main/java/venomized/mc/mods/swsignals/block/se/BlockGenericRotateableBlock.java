package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.SwRotateableBlock;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;

public class BlockGenericRotateableBlock extends SwRotateableBlock implements EntityBlock {
    public BlockGenericRotateableBlock(Properties properties) {
        super(properties.noOcclusion().noCollission());
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SeBlockEntities.BE_U_SIGN.create(pPos, pState);
    }
}
