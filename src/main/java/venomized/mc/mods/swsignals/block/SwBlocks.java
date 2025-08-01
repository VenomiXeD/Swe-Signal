package venomized.mc.mods.swsignals.block;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.test.TestBlock;

public final class SwBlocks {
	// public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

	public static <T extends Block> BlockEntry<T> modelledBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
		return SwSignal.REGISTRATE.get().block(name, blockCreator)
				.properties(prop-> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
				.blockstate((blockTDataGenContext, registrateBlockstateProvider) ->
						registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
								.forAllStates(blockState -> ConfiguredModel.builder()
										.modelFile(new ModelFile.ExistingModelFile(
												registrateBlockstateProvider.modLoc("block/" + blockTDataGenContext.getId().getPath().replace(".","/")),
												registrateBlockstateProvider.models().existingFileHelper)
										).build())).register();
	}

	//For testing purposes
	public static final BlockEntry<TestBlock> BLOCK_TEST = SwSignal.REGISTRATE.get().block("test_test", TestBlock::new)
			.properties(p-> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
			.register();

}
