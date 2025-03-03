package venomized.mc.mods.swsignals.blockentity.sw;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityDwarfSignal extends BlockEntitySignal {
	public BlockEntityDwarfSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_DWARF_SIGNAL.get(), pPos, pBlockState, 4);
	}

	@Override
	public void stepSignalLighting(float partialTick, SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
		switch (createSignalState) {
			case INVALID -> {
				this.lightLevels[0] = Math.max(0, this.lightLevels[0] - partialTick/20);
				this.lightLevels[1] = Math.min(1, this.lightLevels[1] + partialTick/10);
				this.lightLevels[2] = Math.min(1, this.lightLevels[2] + partialTick/10);
				this.lightLevels[3] = Math.max(0, this.lightLevels[3] - partialTick/20);
			}
			case RED -> {
				this.lightLevels[0] = Math.max(0, this.lightLevels[0] - partialTick/20);
				this.lightLevels[1] = Math.max(0, this.lightLevels[1] - partialTick/20);
				this.lightLevels[2] = Math.min(1, this.lightLevels[2] + partialTick/10);
				this.lightLevels[3] = Math.min(1, this.lightLevels[3] + partialTick/10);
			}
			case GREEN -> {
				this.lightLevels[0] = Math.max(0, this.lightLevels[0] - partialTick/20);
				this.lightLevels[1] = Math.min(1, this.lightLevels[1] + partialTick/10);
				this.lightLevels[2] = Math.max(0, this.lightLevels[2] - partialTick/20);
				this.lightLevels[3] = Math.min(1, this.lightLevels[3] + partialTick/10);
			}
			case YELLOW -> {
				this.lightLevels[0] = Math.min(1, this.lightLevels[0] + partialTick/10);
				this.lightLevels[1] = Math.max(0, this.lightLevels[1] - partialTick/20);
				this.lightLevels[2] = Math.max(0, this.lightLevels[2] - partialTick/20);
				this.lightLevels[3] = Math.min(1, this.lightLevels[3] + partialTick/10);
			}
		}
	}
}
