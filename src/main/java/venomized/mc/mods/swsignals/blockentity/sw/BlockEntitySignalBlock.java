package venomized.mc.mods.swsignals.blockentity.sw;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntityBase;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

import java.util.List;
import java.util.Optional;

public abstract class BlockEntitySignalBlock extends SwBlockEntityBase implements IHaveGoggleInformation, ISignalTunerBindable {
	private static final String SIGNAL_BOX_POS_TAG = "signalboxpos";
	public float[] lightLevels;
	private final int lightCount;
	private BlockPos signalBoxPosition;
	private int tick;
	private int remainingTicksAspectChangeDelay;

	public BlockEntitySignalBlock(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState, int lightCount) {
		super(t, pPos, pBlockState);
		this.lightCount = lightCount;
		if (lightCount != -1) {
			this.lightLevels = new float[lightCount];
		}
	}

	private static void worldTick(BlockEntitySignalBlock pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
		pBlockEntity.tick = (pBlockEntity.tick + 1) % 20;
		pBlockEntity.remainingTicksAspectChangeDelay = Math.max(0, pBlockEntity.remainingTicksAspectChangeDelay - 1);
	}

	public static <T extends BlockEntity> void worldTick(Level level, BlockPos blockPos, BlockState blockState, T t) {
		worldTick((BlockEntitySignalBlock) t, level, blockPos, blockState);
	}

	public int getLightCount() {
		return this.lightCount;
	}

	public void setTargetedSignalBoxPosition(BlockPos signalBoxPosition) {
		this.signalBoxPosition = signalBoxPosition;
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
	}

	private BlockEntitySignalBox getConnectedSignalBox() {
		if (signalBoxPosition == null) {
			return null;
		}
		BlockEntity blockEntity = this.getLevel().getBlockEntity(signalBoxPosition);
		if (blockEntity instanceof BlockEntitySignalBox besb) {
			return besb;
		}
		return null;
	}

	public SwedishSignalAspect getCurrentDisplayingAspect() {
		BlockEntitySignalBox connectedSignalBox = this.getConnectedSignalBox();
		if (connectedSignalBox == null) {
			return null;
		}

		return connectedSignalBox.getCurrentAspect();
	}

	public boolean valid() {
		return this.getConnectedSignalBox() != null;
	}

	public boolean blink() {
		return tick > 10;
	}

	public void stepSignalLighting(float partialTick, SwedishSignalAspect aspect, boolean doInvalidBlinking) {
		if (doInvalidBlinking || aspect == null) {
			for (int i = 0; i < this.lightLevels.length; i++) {
				this.lightLevels[i] = this.blink() ? 1 : 0;
			}
			return;
		}
		for (int i = 0; i < lightCount; i++) {
			char s = aspect.getLightPattern().charAt(i);
			switch (s) {
				case 'S':
					this.lightLevels[i] = Math.min(1, this.lightLevels[i] + (partialTick / 10));
					break;
				case 'F':
					if (this.blink()) {
						this.lightLevels[i] = Math.min(1, this.lightLevels[i] + (partialTick / 10));
					} else {
						this.lightLevels[i] = Math.max(0, this.lightLevels[i] - (partialTick / 20));
					}
					break;
				default:
					this.lightLevels[i] = Math.max(0, this.lightLevels[i] - (partialTick / 10));
			}
		}
	}

	/**
	 * this method will be called when looking at a BlockEntity that implemented this
	 * interface
	 *
	 * @param tooltip
	 * @param isPlayerSneaking
	 * @return {@code true} if the tooltip creation was successful and should be
	 * displayed, or {@code false} if the overlay should not be displayed
	 */
	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		Lang.builder().add(Component.literal("WIP")).forGoggles(tooltip);
		// SwedishSignalAspect signalAspect = this.getCurrentAspect();
		// if (signalAspect != null && this.valid()) {
		// 	Lang.builder().add(Component.translatable(signalAspect.getDescription())).forGoggles(tooltip);
		// 	return true;
		// }
		// return false;
		return true;
	}

	/**
	 * Return an {@link AABB} that controls the visible scope of a {@link BlockEntityWithoutLevelRenderer} associated with this {@link BlockEntity}
	 * Defaults to the collision bounding box {@link BlockState#getCollisionShape(BlockGetter, BlockPos)} associated with the block
	 * at this location.
	 *
	 * @return an appropriately size {@link AABB} for the {@link BlockEntity}
	 */
	@Override
	public AABB getRenderBoundingBox() {
		return AABB.ofSize(getBlockPos().getCenter(), 1, 2, 1);
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
		sourceBlockEntity.ifPresent(e -> {
			if (e instanceof BlockEntitySignalBox sb) {
				this.setTargetedSignalBoxPosition(sb.getBlockPos());
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

	@Override
	public boolean isSource() {
		return false;
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
		CompoundTag syncTag = super.getUpdateTag();
		if (signalBoxPosition != null) {
			syncTag.put(SIGNAL_BOX_POS_TAG, NbtUtils.writeBlockPos(signalBoxPosition));
		}
		return syncTag;
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
		if (tag.contains(SIGNAL_BOX_POS_TAG)) {
			this.signalBoxPosition = NbtUtils.readBlockPos(tag.getCompound(SIGNAL_BOX_POS_TAG));
		}
	}

	/**
	 * Called when you receive a TileEntityData packet for the location this
	 * TileEntity is currently in. On the client, the NetworkManager will always
	 * be the remote server. On the server, it will be whomever is responsible for
	 * sending the packet.
	 *
	 * @param net The NetworkManager the packet originated from
	 * @param pkt The data packet
	 */
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		if (pkt.getTag() != null) {
			this.signalBoxPosition = NbtUtils.readBlockPos(pkt.getTag().getCompound(SIGNAL_BOX_POS_TAG));
		}
	}


	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		if (pTag.contains(SIGNAL_BOX_POS_TAG)) {
			this.signalBoxPosition = NbtUtils.readBlockPos(pTag.getCompound(SIGNAL_BOX_POS_TAG));
		}
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		if (signalBoxPosition != null) {
			pTag.put(SIGNAL_BOX_POS_TAG, NbtUtils.writeBlockPos(signalBoxPosition));
		}
	}
}
