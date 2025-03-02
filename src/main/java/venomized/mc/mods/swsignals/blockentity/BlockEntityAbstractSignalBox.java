package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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

	/**
	 * @param pPos
	 * @param pBlockState
	 */

	public BlockEntityAbstractSignalBox(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

	public void setCreateSignalSource(BlockPos createSignalPos) {
		this.createSignalBlockEntityPosition = createSignalPos;
		this.setChanged();
		this.updateSelf();
	}

	public void setSignalBoxSource(BlockPos sourceSignalBoxPos) {
		this.sourceSignalBox = sourceSignalBoxPos;
		this.setChanged();
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
		}
		if (sourceSignalBox != null) {
			pTag.put("source_signalbox_source", NbtUtils.writeBlockPos(sourceSignalBox));
		}
	}

	/**
	 * @param pTag
	 */
	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		if (pTag.contains("create_signal_source")) {
			this.createSignalBlockEntityPosition  = NbtUtils.readBlockPos(pTag.getCompound("create_signal_source"));
		}
		if (pTag.contains("source_signalbox_source")) {
			sourceSignalBox = NbtUtils.readBlockPos(pTag.getCompound("source_signalbox_source"));
		}
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag pTag = super.getUpdateTag();
		if (createSignalBlockEntityPosition != null) {
			pTag.put("create_signal_source", NbtUtils.writeBlockPos(createSignalBlockEntityPosition));
		}
		if (sourceSignalBox != null) {
			pTag.put("source_signalbox_source", NbtUtils.writeBlockPos(sourceSignalBox));
		}
		return pTag;
	}

	/**
	 * Called when the chunk's TE update tag, gotten from {@link BlockEntity#getUpdateTag()}, is received on the client.
	 * <p>
	 * Used to handle this tag in a special way. By default this simply calls {@link BlockEntity#load(CompoundTag)}.
	 *
	 * @param tag The {@link CompoundTag} sent from {@link BlockEntity#getUpdateTag()}
	 */
	@Override
	public void handleUpdateTag(CompoundTag pTag) {
		if (pTag.contains("create_signal_source")) {
			this.createSignalBlockEntityPosition  = NbtUtils.readBlockPos(pTag.getCompound("create_signal_source"));
		}
		if (pTag.contains("source_signalbox_source")) {
			sourceSignalBox = NbtUtils.readBlockPos(pTag.getCompound("source_signalbox_source"));
		}
	}

	/**
	 * Called on the target block entity;
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity
	 * @param mode
	 */
	@Override
	public void onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
		sourceBlockEntity.ifPresent(e->{
			if (e instanceof SignalBlockEntity sbe) {
				this.setCreateSignalSource(sbe.getBlockPos());
				System.out.println("Binded to SignalBlockEntity " + sbe);
			}
		});
	}

	/**
	 * Called on the source block entity;
	 * Signal Box A -> Create Signal; Signal Box A is the target
	 *
	 * @param targetBlockEntity
	 * @param mode
	 */
	@Override
	public void onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode) {

	}


}
