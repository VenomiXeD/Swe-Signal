package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;

public class BlockModernFiveLightSignal extends BlockSignal {
    public BlockModernFiveLightSignal(Properties properties) {
        super(properties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SeBlockEntities.BE_FIVE_LIGHT_SIGNAL.create(pPos, pState);
    }
}
