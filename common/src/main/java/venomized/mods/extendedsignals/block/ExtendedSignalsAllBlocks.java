package venomized.mods.extendedsignals.block;

import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mods.extendedsignals.ExtendedSignalsCore;
import venomized.mods.extendedsignals.blockentity.ExtendedSignalsAllBlockEntities;
import venomized.mods.extendedsignals.core.RegistrateHelper;
import venomized.mods.extendedsignals.create.tracks.ATCController;

public final class ExtendedSignalsAllBlocks {
    // public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

    public static final BlockEntry<BlockTrainConfig> BLOCK_TRAIN_CONFIG = ExtendedSignalsCore.REGISTRATE.get().block("train_config", BlockTrainConfig::new)
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("stone"));
            })
            .simpleItem()
            .register();

    public static final BlockEntry<BlockATCController> BLOCK_ATC_CONTROLLER = ExtendedSignalsCore.REGISTRATE.get().block("atc_controller", BlockATCController::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("stone"));
            })
            .item((block, prop) -> new TrackTargetingBlockItem(block, prop, ATCController.ATC))
            .build()
            .register();
    public static final BlockEntry<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = RegistrateHelper.modelledBlock(
                    ExtendedSignalsCore.REGISTRATE.get(), "crossing_controller", BlockRailroadCrossingController::new)
            .register();

    public static void init() {
        ExtendedSignalsAllBlockEntities.init();
    }

    //For testing purposes
    // public static final BlockEntry<TestBlock> BLOCK_TEST = SwSignal.REGISTRATE.get().block("test_test", TestBlock::new)
    // 		.properties(p-> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
    // 		.register();
//
}
