package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
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
		getVariantBuilder(SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get()).forAllStates(blockState ->
				ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(modLoc("block/2l_signal"))).build()
		);
		getVariantBuilder(SwBlocks.BLOCK_THREE_LIGHT_SIGNAL.get()).forAllStates(blockState ->
				ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(modLoc("block/3l_signal"))).build()
		);
	}
}
