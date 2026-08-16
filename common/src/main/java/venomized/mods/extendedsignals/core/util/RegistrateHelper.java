package venomized.mods.extendedsignals.core.util;

import com.simibubi.create.Create;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.core.data.ExtendedSignalsLang;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegistrateHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrateHelper.class);

    public static <T extends Block> BlockBuilder<T, Registrate> modelledBlock(Registrate registrateInstance, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        return registrateInstance
                .block(name, blockCreator)
                .properties(prop -> BlockBehaviour.Properties.of().destroyTime(1f))
                .blockstate((blockTDataGenContext, registrateBlockstateProvider) -> {
                    //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {
                    String path = "block/" + name.replace(".", "/");
                    ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

                    ExtendedSignals.LOGGER.info("modelled block path: {}", path);

                    registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
                            .forAllStates(blockState -> ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(
                                            loc
                                    ))
                                    .build());
                    //}
                })
                .item()
                .model((ctx, prov) -> {
                    if (prov.existingFileHelper.exists(prov.modLoc("item/" + name), PackType.CLIENT_RESOURCES, ".json", "models")) {
                        // Custom definition already provided, skip
                        return;
                    }
                    String path = "block/" + name.replace(".", "/");
                    ResourceLocation loc = prov.modLoc(path);
                    if (prov.existingFileHelper.exists(loc, PackType.CLIENT_RESOURCES, ".json", "models")) {
                        prov.withExistingParent(name, loc)
                                .transforms()
                                .transform(ItemDisplayContext.FIXED)
                                .rotation(0, 180, 0);
                        return;
                    }
                    prov.cubeAll(ctx.getName(), prov.mcLoc("block/iron_block"));
                }).build();
        // .item()
        // .build();
        // .defaultModel()
        // .simpleItem()
        // .item()
        // .tab(SwSignal.SW_SIGNAL_TAB.getKey())
        // .build();
    }

    public static <T extends Block> BlockBuilder<T, Registrate> genericCustomModelledBlock(Registrate registrateInstance, String nation, String assetType, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        String properName = name.replaceAll("(\\d+)l", "$1 Light")
                .replaceAll("_post_(\\d+)_?", " (Post $1)")
                .replace('_', ' ');

        return registrateInstance
                .block("%s.%s".formatted(nation, name), blockCreator)
                .lang("(%s) %s".formatted(ExtendedSignalsLang.fromISO639_1(nation), properName))
                .properties(prop -> BlockBehaviour.Properties.of()
                        .destroyTime(1f))
                .blockstate(customBlockModelProvider(assetType, nation, name))
                .item()
                .model(modelLocator(assetType, nation, name))
                .build();

    }

    //public static <T extends Block, BE extends BlockEntity> BlockBuilder<T, Registrate> genericModelledBlock(Registrate registrateInstance, String nation, String assetType, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator, BlockEntityBuilder.BlockEntityFactory<BE> blockEntityFactory) {
    //    String properName = name.replaceAll("(\\d+)l", "$1 Light")
    //            .replaceAll("_post_(\\d+)_?", " (Post $1)")
    //            .replace('_', ' ');

    //    Registrate blockEntityType = registrateInstance.blockEntity("name", blockEntityFactory)
    //            .renderer(() -> RendererGeneric::new)
    //            .build();
    //
    //    registrateInstance
    //            .
    //            .block("%s.%s".formatted(nation, name), blockCreator)
    //            .lang("(%s) %s".formatted(ExtendedSignalsLang.fromISO639_1(nation), properName))
    //            .properties(prop -> BlockBehaviour.Properties.of()
    //                    .destroyTime(1f))
    //            .blockstate(customBlockModelProvider(assetType, nation, name))
    //            .item()
    //            .model(modelLocator(assetType, nation, name))
    //            .build();

    //}

    public static <T extends BlockModelled> BlockBuilder<T, Registrate> genericCustomModelledBlock(Registrate registrateInstance, String nation, String assetType, String name, Function<Supplier<BiFunction<BlockPos, BlockState, BlockEntity>>, NonNullFunction<BlockBehaviour.Properties, T>> blockFactory, BlockEntityBuilder.BlockEntityFactory<? extends ModelBlockEntity> blockEntityFactory) {
        String properName = name.replaceAll("(\\d+)l", "$1 Light")
                .replaceAll("_post_(\\d+)_?", " (Post $1)")
                .replace('_', ' ');

        var blockRef = new Object() {
            BlockBuilder<T, Registrate> block = null;
            BlockEntityBuilder<? extends ModelBlockEntity, Registrate> blockentity = null;
        };
        blockRef.block = registrateInstance
                .block("%s.%s".formatted(nation, name), blockFactory.apply(() -> blockRef.blockentity.getEntry()::create))
                .lang("(%s) %s".formatted(ExtendedSignalsLang.fromISO639_1(nation), properName))
                .properties(prop -> BlockBehaviour.Properties.of()
                        .destroyTime(1f))
                .blockstate(customBlockModelProvider(assetType, nation, name))
                .item()
                .model(modelLocator(assetType, nation, name))
                .build();

        blockRef.blockentity = registrateInstance.blockEntity(name, blockEntityFactory)
                .renderer(() -> RendererGeneric::new)
                .validBlock(() -> blockRef.block.getEntry());


        blockRef.blockentity.register();
        return blockRef.block;
        // .item()
        // .model(modelLocator(assetType, nation, name))
        // .build();
    }

    private static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> customBlockModelProvider(final String assetType, final String nation, final String name) {
        return (blockTDataGenContext, registrateBlockstateProvider) -> {
            //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {

            String path = "block/%s/%s/%s".formatted(assetType, nation, name.replace(".", "/"));
            ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

            if (!registrateBlockstateProvider.models().existingFileHelper.exists(
                    loc, PackType.CLIENT_RESOURCES, ".json", "models")) {
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
            LOGGER.info("Looking for item texture: {}", loc);
            if (prov.existingFileHelper.exists(loc, PackType.CLIENT_RESOURCES, ".png", "textures")) {
                prov.handheld(ctx::get, loc);
                return;
            }
            LOGGER.info("Not found");
            prov.cubeAll(ctx.getName(), prov.mcLoc("block/iron_block"));
        };
    }

    public static <T extends BlockEntity> BlockEntityBuilder<T, Registrate> simpleBlockEntity(Registrate registrateInstance, String beName, BlockEntityBuilder.BlockEntityFactory<T> beFactory, NonNullSupplier<? extends Block> validBlock) {
        return registrateInstance
                .blockEntity(beName, beFactory)
                .validBlocks(validBlock);
    }
}
