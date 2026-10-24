package venomized.mods.extendedsignals.hu.block.signals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.hu.blockentity.HungaryBlockEntities;

public class BlockABS3LightSignal extends BlockSignal {
    /**
     * @param pProperties
     */
    public BlockABS3LightSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return HungaryBlockEntities.ABS_THREE_LIGHT_SIGNAL.create(pos, state);
    }
}
