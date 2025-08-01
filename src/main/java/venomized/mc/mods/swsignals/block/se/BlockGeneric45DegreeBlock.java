package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.Sw45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockGeneric45DegreeBlock extends Sw45DegreeBlock implements EntityBlock {
	public BlockGeneric45DegreeBlock(Properties properties) {
		super(properties.noOcclusion().noCollission());
	}

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return SwBlockEntities.BE_U_SIGN.create(pPos, pState);
	}
}
