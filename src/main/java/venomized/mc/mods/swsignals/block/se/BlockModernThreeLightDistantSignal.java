package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockModernThreeLightDistantSignal extends BlockSignal {
    public BlockModernThreeLightDistantSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwBlockEntities.BE_THREE_LIGHT_DISTANT_SIGNAL.create(pPos, pState);
    }
}
