package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.create.tracks.*;

public final class CoreBlocks {
    public static Registrate registrate() {
        return ExtendedSignalsCore.REGISTRATE.get();
    }
    // public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

    public static final BlockEntry<BlockTrainConfig> BLOCK_TRAIN_CONFIG = ExtendedSignalsCore.REGISTRATE.get()
            .block("train_config", BlockTrainConfig::new)
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("stone"));
            })
            .simpleItem()
            .register();

    public static final BlockEntry<BlockATCController> ATC_CONTROLLER = ExtendedSignalsCore.REGISTRATE.get()
            .block("atc_controller", BlockATCController::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("stone"));
            })
            .item((block, prop) -> new TrackTargetingBlockItem(block, prop, ATCController.ATC))
            .build()
            .register();

    public static final BlockEntry<BlockCrossingController> CROSSING_CONTROLLER = ExtendedSignalsCore.REGISTRATE.get()
            .block("crossing_controller", BlockCrossingController::new)
            .simpleItem()
            .register();

    public static final BlockEntry<BlockRepeaterCreateSignal> REPEATER_SIGNAL = ExtendedSignalsCore.REGISTRATE.get()
            .block("repeater_signal", BlockRepeaterCreateSignal::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("stone"));
            })
            .item(TrackTargetingBlockItem.ofType(CoreEdgePoints.REPEATER))
            .build()
            .register();

    public static final BlockEntry<BlockSpeedModifier> SPEED_MODIFIER = ExtendedSignalsCore.REGISTRATE.get()
            .block("speed_modifier", BlockSpeedModifier::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("iron"));
            })
            .item(TrackTargetingBlockItem.ofType(CoreEdgePoints.SPEED_MODIFIER))
            .build()
            .register();
    public static final BlockEntry<BlockPathTrainDetector> PATH_TRAIN_DETECTOR = registrate()
            .block("train_path_detector", BlockPathTrainDetector::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .item(TrackTargetingBlockItem.ofType(CoreEdgePoints.PATH_TRAIN_DETECTOR))
            .build()
            .register();


    public static void init() {
        CoreBlockEntities.init();
    }

    //For testing purposes
    // public static final BlockEntry<TestBlock> BLOCK_TEST = SwSignal.REGISTRATE.get().block("test_test", TestBlock::new)
    // 		.properties(p-> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
    // 		.register();
//
}
