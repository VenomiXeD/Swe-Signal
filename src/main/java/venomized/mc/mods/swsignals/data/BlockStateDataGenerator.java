package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.SwBlocks;

public class BlockStateDataGenerator extends BlockStateProvider {
	public BlockStateDataGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, SwSignal.MOD_ID, exFileHelper);
	}

	/**
	 *
	 */
	@Override
	protected void registerStatesAndModels() {
		this.cubeAll(SwBlocks.BLOCK_SW_SIGNAL_BOX.get());
	}
}
