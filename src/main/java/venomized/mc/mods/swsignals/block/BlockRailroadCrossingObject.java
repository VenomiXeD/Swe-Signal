package venomized.mc.mods.swsignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityRailroadCrossingSignal;

public abstract class BlockRailroadCrossingObject extends Sw45DegreeBlock implements EntityBlock {
	public BlockRailroadCrossingObject(Properties properties) {
		super(properties);
	}
	/**
	 * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	 * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	 *
	 * @param pState
	 * @deprecated call via {@link BlockStateBase#getRenderShape}
	 * whenever possible. Implementing/overriding is fine.
	 */
	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
}
