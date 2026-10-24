package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockSpeedSign extends BlockModelled {
    /**
     * @param pProperties
     */
    public BlockSpeedSign(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SwedenBlockEntities.SPEED_SIGN.create(pos, state);
    }
}
