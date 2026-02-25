package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.api.schematic.nbt.PartialSafeNBT;
import com.simibubi.create.foundation.utility.IInteractionChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.client.sound.train.TrainSound;
import venomized.mc.mods.swsignals.client.sound.train.TrainSounds;

public class BlockEntityTrainConfig extends SwBlockEntity implements PartialSafeNBT, IInteractionChecker {
    public BlockEntityTrainConfig(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public TrainSound trainSound() {
        return TrainSounds.TRAIN_X60.get().create();
    }

    /**
     * @param compound
     */
    @Override
    public void writeSafe(CompoundTag compound, HolderLookup.Provider registries) {
    }

    /**
     * @param pTag
     */
    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
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
