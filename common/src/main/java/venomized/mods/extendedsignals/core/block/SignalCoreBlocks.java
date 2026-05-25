package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.create.tracks.ATCController;
import venomized.mods.extendedsignals.core.create.tracks.RepeaterSignal;
import venomized.mods.extendedsignals.core.create.tracks.SpeedModifier;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;

public final class SignalCoreBlocks {
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
    public static final BlockEntry<BlockRepeaterCreateSignal> REPEATER_SIGNAL = ExtendedSignalsCore.REGISTRATE.get().block("repeater_signal", BlockRepeaterCreateSignal::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("stone"));
            })
            .item(TrackTargetingBlockItem.ofType(RepeaterSignal.REPEATER))
            .build()
            .register();
    public static final BlockEntry<BlockSpeedModifier> SPEED_MODIFIER = ExtendedSignalsCore.REGISTRATE.get().block("speed_modifier", BlockSpeedModifier::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .blockstate((ctx, prov) -> {
                prov.simpleBlock(ctx.get(), new ModelFile.UncheckedModelFile("iron"));
            })
            .item(TrackTargetingBlockItem.ofType(SpeedModifier.SPEED_MODIFIER))
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
