package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlockEntityRailroadCrossingController extends SwBlockEntityBase implements ISignalTunerBindable {
	private boolean powered;
	public void setPowered(boolean powered) {
		if (this.powered != powered) {
			this.powered = powered;
			this.updateSelf();
		}
	}

	public BlockEntityRailroadCrossingController(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_RAILROAD_CROSSING_CONTROLLER.get(), pPos, pBlockState);
	}

	/**
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity source block destination
	 * @param mode
	 */
	@Override
	public void onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {

	}

	/**
	 * Signal Box A -> Create Signal; Signal Box A is the target
	 *
	 * @param targetBlockEntity target block destination
	 * @param mode
	 */
	@Override
	public void onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode) {

	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putBoolean("powered", powered);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		this.powered = pTag.getBoolean("powered");
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag t = new CompoundTag();
		this.saveAdditional(t);
		return t;
	}

	/**
	 * Called when the chunk's TE update tag, gotten from {@link BlockEntity#getUpdateTag()}, is received on the client.
	 * <p>
	 * Used to handle this tag in a special way. By default this simply calls {@link BlockEntity#load(CompoundTag)}.
	 *
	 * @param tag The {@link CompoundTag} sent from {@link BlockEntity#getUpdateTag()}
	 */
	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.handleUpdateTag(tag);
		this.powered = tag.getBoolean("powered");
	}

	public boolean isPowered() {
		return this.powered;
	}
}
