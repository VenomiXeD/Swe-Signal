package venomized.mods.extendedsignals.core.blockentity;

import com.simibubi.create.api.schematic.nbt.PartialSafeNBT;
import com.simibubi.create.foundation.utility.IInteractionChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityTrainConfig extends CoreBlockEntity implements PartialSafeNBT, IInteractionChecker {
    public BlockEntityTrainConfig(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
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
