package venomized.mods.extendedsignals.se.block.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.block.BlockSwedenSignal;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class Block3LightDistantSignalModern extends BlockSwedenSignal implements EntityBlock {
    /**
     * @param properties
     */
    public Block3LightDistantSignalModern(Properties properties) {
        super(properties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SwedenBlockEntities.DISTANT_THREE_LIGHT.get().create(pPos, pState);
    }
}
