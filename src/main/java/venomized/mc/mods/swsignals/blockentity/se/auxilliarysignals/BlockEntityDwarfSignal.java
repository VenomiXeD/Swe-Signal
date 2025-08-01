package venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityDwarfSignal extends BlockEntitySignal {
	public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState) {
		this(t, pPos, pBlockState, 4);
	}

	public BlockEntityDwarfSignal(BlockEntityType t, BlockPos pPos, BlockState pBlockState, int lightCount) {
		super(t, pPos, pBlockState, lightCount);
	}

	@Override
	public void stepSignalLighting(float partialTick, SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
		switch (createSignalState) {
			case INVALID -> {
				this.lightLevels[0] = Math.max(0, this.lightLevels[0] - partialTick / 20);
				this.lightLevels[1] = Math.min(1, this.lightLevels[1] + partialTick / 10);
				this.lightLevels[2] = Math.min(1, this.lightLevels[2] + partialTick / 10);
				this.lightLevels[3] = Math.max(0, this.lightLevels[3] - partialTick / 20);
			}
			case RED -> {
				this.lightLevels[0] = Math.max(0, this.lightLevels[0] - partialTick / 20);
				this.lightLevels[1] = Math.max(0, this.lightLevels[1] - partialTick / 20);
				this.lightLevels[2] = Math.min(1, this.lightLevels[2] + partialTick / 10);
				this.lightLevels[3] = Math.min(1, this.lightLevels[3] + partialTick / 10);
			}
			case GREEN -> {
				this.lightLevels[0] = Math.max(0, this.lightLevels[0] - partialTick / 20);
				this.lightLevels[1] = Math.min(1, this.lightLevels[1] + partialTick / 10);
				this.lightLevels[2] = Math.max(0, this.lightLevels[2] - partialTick / 20);
				this.lightLevels[3] = Math.min(1, this.lightLevels[3] + partialTick / 10);
			}
			case YELLOW -> {
				this.lightLevels[0] = Math.min(1, this.lightLevels[0] + partialTick / 10);
				this.lightLevels[1] = Math.max(0, this.lightLevels[1] - partialTick / 20);
				this.lightLevels[2] = Math.max(0, this.lightLevels[2] - partialTick / 20);
				this.lightLevels[3] = Math.min(1, this.lightLevels[3] + partialTick / 10);
			}
		}
	}
}
