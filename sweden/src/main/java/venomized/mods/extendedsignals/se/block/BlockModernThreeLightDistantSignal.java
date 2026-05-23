package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.ExtendedSignalBlock;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

public class BlockModernThreeLightDistantSignal extends ExtendedSignalBlock implements EntityBlock {
    public BlockModernThreeLightDistantSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ExtendedSignalsSwedenBlockEntities.DISTANT_THREE_LIGHT.create(pPos, pState);
    }
}
