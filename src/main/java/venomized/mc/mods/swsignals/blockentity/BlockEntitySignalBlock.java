package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.SwedishSignalAspect;

import java.util.List;

public abstract class BlockEntitySignalBlock extends BlockEntity implements IHaveGoggleInformation {
	private int lightCount;

	private SwedishSignalAspect cachedAspect;

	private BlockPos signalBlockPosition;

	private double yRot;

	private int tick;

	public float[] lightLevels;

	public BlockEntitySignalBlock(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState, int lightCount) {
		super(t, pPos, pBlockState);
		this.lightCount = lightCount;
		this.lightLevels = new float[lightCount];
	}

	public int getLightCount() {
		return this.lightCount;
	}

	public void setTargetedSignal(BlockPos swSignalPos) {
		this.signalBlockPosition = swSignalPos;
		this.setChanged();
	}

	private SignalBlockEntity getConnectedSignalBlock() {
		if (signalBlockPosition == null) {
			return null;
		}
		BlockEntity b = getLevel().getBlockEntity(signalBlockPosition);
		if (b instanceof SignalBlockEntity) {
			return (SignalBlockEntity) b;
		}
		return null;
	}

	public boolean valid() {
		return signalBlockPosition != null && this.getCurrentAspect() != null && this.getConnectedSignalBlock() != null;
	}

	public SwedishSignalAspect getCurrentAspect() {
		SwedishSignalAspect result;

		SignalBlockEntity b = this.getConnectedSignalBlock();
		if (b == null) {
			return null;
		}
		else if (b.getSignal() == null) {
			return cachedAspect;
		}

		SignalBlockEntity.SignalState state = b.getState();
		result = switch (state) {
			case GREEN -> SwedishSignalAspect.PROCEED_80;
			case RED -> SwedishSignalAspect.STOP;
			case YELLOW -> SwedishSignalAspect.PROCEED_40_CAUTION;
			case INVALID -> null;
		};

		this.cachedAspect = result;
		return result;
	}

	public boolean blink() {
		return tick > 10;
	}



	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	 * many blocks change at once. This compound comes back to you clientside in
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		tag.putDouble("yRot", yRot);
		if (signalBlockPosition != null) {
			tag.put("signal_target_position", NbtUtils.writeBlockPos(signalBlockPosition));
		}
		return tag;
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
		this.yRot = tag.getDouble("yRot");
		if (tag.contains("signal_target_position")) {
			this.signalBlockPosition = NbtUtils.readBlockPos(tag.getCompound("signal_target_position"));
		}
	}

	/**
	 * @param pTag
	 */
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putDouble("yRot", yRot);
		if (signalBlockPosition != null) {
			pTag.putIntArray("signalpos", new int[]{signalBlockPosition.getX(), signalBlockPosition.getY(), signalBlockPosition.getZ()});
		}
		System.out.println("saved signal block entity: " + this.getBlockPos());
		System.out.println("saved signal data: " + signalBlockPosition);
	}

	/**
	 * @param pTag
	 */
	@Override
	public void load(CompoundTag pTag) {
		System.out.println("loading signal block entity: " + this.getBlockPos());
		super.load(pTag);
		yRot = pTag.getDouble("yRot");
		if (pTag.contains("signalpos")) {
			int[] pos = pTag.getIntArray("signalpos");
			signalBlockPosition = new BlockPos(pos[0], pos[1], pos[2]);
		}

		System.out.println("signal block position:" + signalBlockPosition);
	}

	private static void worldTick(BlockEntitySignalBlock pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
		pBlockEntity.tick = (pBlockEntity.tick + 1) % 20;
	}

	public static <T extends BlockEntity> void worldTick(Level level, BlockPos blockPos, BlockState blockState, T t) {
		worldTick((BlockEntitySignalBlock)t, level, blockPos, blockState);
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
		SwedishSignalAspect signalAspect = this.getCurrentAspect();
		if (signalAspect != null && this.valid()) {
			tooltip.add(Component.translatable(signalAspect.getDescription()));
			return true;
		}
		return false;
	}
}
