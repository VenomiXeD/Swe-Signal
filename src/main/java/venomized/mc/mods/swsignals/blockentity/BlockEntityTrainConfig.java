package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.foundation.utility.IInteractionChecker;
import com.simibubi.create.foundation.utility.IPartialSafeNBT;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.client.sound.ITrainSound;

public class BlockEntityTrainConfig extends SwBlockEntity implements IPartialSafeNBT, IInteractionChecker {
    public BlockEntityTrainConfig(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public ITrainSound trainSound() {
        return null;
    }

    /**
     * @param compound
     */
    @Override
    public void writeSafe(CompoundTag compound) {
    }

    /**
     * @param pTag
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean canPlayerUse(Player player) {
        return false;
    }
}
