package venomized.mods.extendedsignals.core.registry;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.block.IAutoBlockEntityBlock;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;

import java.util.Optional;

public class ExtendedSignalsRegistrate extends Registrate {
    public ExtendedSignalsRegistrate(String modid) {
        super(modid);
    }

    /**
     * Auto-hooks listeners
     *
     * @param modId
     * @return
     */

    // Copied from Registrate create method, with the type being replaced
    public static ExtendedSignalsRegistrate create(String modId) {
        var ret = new ExtendedSignalsRegistrate(modId);
        Optional<IEventBus> modEventBus = ModList.get().getModContainerById(modId).map(ModContainer::getEventBus);
        modEventBus.ifPresentOrElse(ret::registerEventListeners, () -> {
            String message = "# [ExtendedSignalsRegistrate] Failed to register eventListeners for mod " + modId + ", This should be reported to this mod's dev #";
            StringBuilder hashtags = new StringBuilder().append("#".repeat(message.length()));
            ExtendedSignals.LOGGER.fatal(hashtags.toString());
            ExtendedSignals.LOGGER.fatal(message);
            ExtendedSignals.LOGGER.fatal(hashtags.toString());
        });
        return ret;
    }

    public <B extends Block & IAutoBlockEntityBlock, E extends BlockEntity> BlockBlockEntityCoBuilder<B, E, ExtendedSignalsRegistrate> blockWithBlockEntityAttached(
            String nation,
            String assetType,
            String name,
            NonNullFunction<BlockBehaviour.Properties, B> blockFactory,
            BlockEntityBuilder.BlockEntityFactory<E> blockEntityFactory
    ) {
        BlockBuilder<B, ExtendedSignalsRegistrate> signalBlockBuilder = this.block(this, "%s.%s".formatted(nation, name), blockFactory);
        BlockEntityBuilder<E, ExtendedSignalsRegistrate> blockEntityBuilder = this.blockEntity(this, name, blockEntityFactory);


        signalBlockBuilder.properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
                .blockstate(customBlockModelProvider(assetType, nation, name))
                .item()
                .model(modelLocator(assetType, nation, name))
                .build();

        blockEntityBuilder.validBlock(signalBlockBuilder.asSupplier());
        signalBlockBuilder.onRegisterAfter(blockEntityBuilder.getRegistryKey(), b -> b.setBlockEntityType(blockEntityBuilder.getEntry()::create));

        return new BlockBlockEntityCoBuilder<>(signalBlockBuilder, blockEntityBuilder);
    }

    public <B extends Block & IAutoBlockEntityBlock, E extends BlockEntity> BlockBlockEntityCoBuilder<B, E, ExtendedSignalsRegistrate> blockWithBlockEntityAttached(
            String nation,
            String assetType,
            String name,
            NonNullFunction<BlockBehaviour.Properties, B> blockFactory,
            BlockEntityBuilder.BlockEntityFactory<E> blockEntityFactory,
            NonNullSupplier<NonNullFunction<Context, BlockEntityRenderer<E>>> renderer
    ) {
        return blockWithBlockEntityAttached(nation, assetType, name, blockFactory, blockEntityFactory)
                .actOnRight(e -> renderer.get());
    }

    public BlockBlockEntityCoBuilder<BlockSignal, BlockEntitySignal<?>, ExtendedSignalsRegistrate> blockSignal(
            String nation,
            String assetType,
            String name,
            BlockEntityBuilder.BlockEntityFactory<BlockEntitySignal<?>> blockEntityFactory) {
        return blockWithBlockEntityAttached(
                nation, assetType, name, BlockSignal::new, blockEntityFactory, () -> RendererSignal::new
        );
    }

    public BlockBlockEntityCoBuilder<BlockSignal, BlockEntitySignal<?>, ExtendedSignalsRegistrate> blockSignal(
            String nation,
            String assetType,
            String name,
            BlockEntityBuilder.BlockEntityFactory<BlockEntitySignal<?>> blockEntityFactory,
            NonNullSupplier<NonNullFunction<Context, BlockEntityRenderer<BlockEntitySignal<?>>>> renderer) {
        return blockWithBlockEntityAttached(
                nation, assetType, name, BlockSignal::new, blockEntityFactory, renderer
        );
    }

    private static <T extends net.minecraft.world.level.block.Block> NonNullBiConsumer<DataGenContext<net.minecraft.world.level.block.Block, T>, RegistrateBlockstateProvider> customBlockModelProvider(final String assetType, final String nation, final String name) {
        return (blockTDataGenContext, registrateBlockstateProvider) -> {
            //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {

            String path = "block/%s/%s/%s".formatted(assetType, nation, name.replace(".", "/"));
            ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

            if (!registrateBlockstateProvider.models().existingFileHelper.exists(
                    loc, PackType.CLIENT_RESOURCES, ".json", "models")) {
                ExtendedSignals.LOGGER.fatal("Missing model file at {} for {}", loc.toString(), path);
                return;
            }

            registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
                    .forAllStates(blockState -> new ConfiguredModel[]{ConfiguredModel.builder()
                            .modelFile(registrateBlockstateProvider.models().getExistingFile(loc))
                            .buildLast()});
            //}
        };
    }

    private static NonNullBiConsumer<DataGenContext<Item, BlockItem>, RegistrateItemModelProvider> modelLocator(String assetType, String nation, String name) {
        return (ctx, prov) -> {
            if (prov.existingFileHelper.exists(
                    prov.modLoc("item/" + "%s.%s".formatted(nation, name)), PackType.CLIENT_RESOURCES, ".json",
                    "models"
            )) {
                // Custom definition already provided, skip
                return;
            }

            // PLACEMENT: <root>/textures/item/signals/<nation>/<signalblockname>.png
            String path = "item/" + assetType + "/" + "%s/%s".formatted(nation, name.replace(".", "/"));
            ResourceLocation loc = prov.modLoc(path);
            ExtendedSignals.LOGGER.info("Looking for item texture: {}", loc);
            if (prov.existingFileHelper.exists(loc, PackType.CLIENT_RESOURCES, ".png", "textures")) {
                prov.handheld(ctx::get, loc);
                return;
            }
            ExtendedSignals.LOGGER.info("Not found");
            prov.cubeAll(ctx.getName(), prov.mcLoc("block/iron_block"));
        };
    }

}
