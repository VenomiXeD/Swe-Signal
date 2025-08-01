package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BlockEntityAbstractSignalBox extends SwBlockEntityBase implements ISignalTunerBindable {
	/**
	 * Absolute state reader (GO / STOP)
	 */
	@Nullable
	protected BlockPos createSignalBlockEntityPosition;

	/**
	 * Detailed state reader (repeat aspects etc);
	 * May be null
	 */
	@Nullable
	protected BlockPos sourceSignalBox;

	protected boolean doLoadExternalSignalBox = true;

	/**
	 * @param pPos
	 * @param pBlockState
	 */
	public BlockEntityAbstractSignalBox(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

	public SignalBlockEntity.SignalState getCreateSignalState() {
		if (createSignalBlockEntityPosition == null) {
			return SignalBlockEntity.SignalState.INVALID;
		}

		SignalBlockEntity createSignalBlockEntity = this.getCreateSignalBlockEntity();
		if (createSignalBlockEntity == null) {
			return SignalBlockEntity.SignalState.INVALID;
		}

		SignalBlockEntity.SignalState s = createSignalBlockEntity.getState();

		if (s == null) {
			return SignalBlockEntity.SignalState.INVALID;
		}
		return s;
	}

	public SignalBlockEntity getCreateSignalBlockEntity() {
		if (createSignalBlockEntityPosition == null) {
			return null;
		}

		Level level = this.getLevel();
		if (level == null) {
			return null;
		}

		BlockEntity blockEntity = level.getBlockEntity(createSignalBlockEntityPosition);
		if (blockEntity instanceof SignalBlockEntity sbe) {
			return sbe;
		}

		return null;
	}

	public BlockEntityAbstractSignalBox getSignalBoxBlockEntity() {
		if (this.sourceSignalBox == null) {
			return null;
		}

		BlockEntity blockEntity = this.getLevel().getBlockEntity(sourceSignalBox);
		if (blockEntity instanceof BlockEntityAbstractSignalBox sb) {
			return sb;
		}
		return null;
	}

	@Override
	public void setRemoved() {
		if (!level.isClientSide() && this.doLoadExternalSignalBox && sourceSignalBox != null) {
			ChunkPos chunkPos = new ChunkPos(sourceSignalBox);
			((ServerLevel) level).setChunkForced(chunkPos.x, chunkPos.z, false);
		}
		super.setRemoved();
	}


	public void setCreateSignalSource(BlockPos createSignalPos) {
		this.createSignalBlockEntityPosition = createSignalPos;
		this.updateSelf();
	}

	public void setSignalBoxSource(BlockPos sourceSignalBoxPos) {
		this.sourceSignalBox = sourceSignalBoxPos;
		this.updateSelf();
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * @param pTag
	 */
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		if (createSignalBlockEntityPosition != null) {
			pTag.put("create_signal_source", NbtUtils.writeBlockPos(createSignalBlockEntityPosition));
		} else {
			pTag.putBoolean("create_signal_source_missing", true);
		}
		if (sourceSignalBox != null) {
			pTag.put("source_signalbox_source", NbtUtils.writeBlockPos(sourceSignalBox));
		} else {
			pTag.putBoolean("source_signalbox_source_missing", true);
		}
	}

	@Override
	public void onLoad() {
		// attempt to load the signal box
		this.getSignalBoxBlockEntity();
		super.onLoad();
	}

	/**
	 * @param pTag
	 */
	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);

		if (pTag.contains("create_signal_source")) {
			this.createSignalBlockEntityPosition = NbtUtils.readBlockPos(pTag.getCompound("create_signal_source"));
		} else {
			this.createSignalBlockEntityPosition = null;
		}

		if (pTag.contains("source_signalbox_source")) {
			sourceSignalBox = NbtUtils.readBlockPos(pTag.getCompound("source_signalbox_source"));
		} else {
			sourceSignalBox = null;
		}
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for
	 * initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in
	 * {@link handleUpdateTag}
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag pTag = super.getUpdateTag();
		this.saveAdditional(pTag);
		return pTag;
	}

	/**
	 * Called on the target block entity;
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity
	 * @param mode
	 * @return
	 */
	@Override
	public Pair<InteractionResult, Component> onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
		if (mode == SignalTunerMode.DISCONNECT_ALL) {
			setSignalBoxSource(null);
			setCreateSignalSource(null);
			return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully unbound all"));
		}
		if (sourceBlockEntity.isPresent()) {
			ISignalTunerBindable be = sourceBlockEntity.get();
			if (be instanceof SignalBlockEntity sbe) {
				this.setCreateSignalSource(sbe.getBlockPos());
				return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully bound to signal"));
			}
			if (be instanceof BlockEntityAbstractSignalBox sb) {
				this.setSignalBoxSource(sb.getBlockPos());
				return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully bound to signal box"));
			}
		}
		return Pair.of(InteractionResult.PASS, Component.literal("No block entity bound"));
	}
}
