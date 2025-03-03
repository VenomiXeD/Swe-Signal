package venomized.mc.mods.swsignals.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityRailroadCrossingSignal;

public abstract class BlockRailroadCrossingObjectBase extends SwAbstractBlock implements EntityBlock {
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityRailroadCrossingSignal(pPos, pState);
	}

	public BlockRailroadCrossingObjectBase() {
		super(Properties.copy(Blocks.IRON_BLOCK));
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
