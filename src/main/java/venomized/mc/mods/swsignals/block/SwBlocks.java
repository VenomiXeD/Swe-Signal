package venomized.mc.mods.swsignals.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mc.mods.swsignals.SwSignal;

public final class SwBlocks {
    // public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

    public static <T extends Block> BlockBuilder<T, Registrate> modelledBlock(String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        return SwSignal.REGISTRATE.get()
                .block(name, blockCreator)
                .properties(prop -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
                .blockstate((blockTDataGenContext, registrateBlockstateProvider) -> {
                    //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {
                    String path = "block/" + name.replace(".", "/");
                    ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

                    SwSignal.LOGGER.info("modelled block path: {}", path);

                    registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
                            .forAllStates(blockState -> ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(
                                    loc
                            )).build());
                    //}
                })
                .simpleItem();
                // .item()
                // .build();
                // .defaultModel()
                // .simpleItem()
                // .item()
                // .tab(SwSignal.SW_SIGNAL_TAB.getKey())
                // .build();
    }

    public static <T extends Block> BlockBuilder<T, Registrate> signalBlock(String nationID, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        return SwSignal.REGISTRATE.get()
                .block(name, blockCreator)
                .properties(prop -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
                .blockstate((blockTDataGenContext, registrateBlockstateProvider) -> {
                    //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {
                    String path = "block/signals/" + nationID + "/" + name;
                    ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

                    SwSignal.LOGGER.info("modelled block path: {}", path);

                    registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
                            .forAllStates(blockState -> ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(
                                    loc
                            )).build());
                    //}
                });
        // .defaultModel()
        // .simpleItem()
        // .item()
        // .tab(SwSignal.SW_SIGNAL_TAB.getKey())
        // .build();
    }

    //For testing purposes
    // public static final BlockEntry<TestBlock> BLOCK_TEST = SwSignal.REGISTRATE.get().block("test_test", TestBlock::new)
    // 		.properties(p-> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
    // 		.register();
//
}
