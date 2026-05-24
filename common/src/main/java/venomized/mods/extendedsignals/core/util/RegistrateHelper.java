package venomized.mods.extendedsignals.core.util;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.blockentity.ExtendedSignalsCoreBlockEntities;
import venomized.mods.extendedsignals.core.data.SwSignalLang;

public class RegistrateHelper {
    public static <T extends Block> BlockBuilder<T, Registrate> modelledBlock(Registrate registrateInstance, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        return registrateInstance
                .block(name, blockCreator)
                .properties(prop -> BlockBehaviour.Properties.of().destroyTime(1f))
                .blockstate((blockTDataGenContext, registrateBlockstateProvider) -> {
                    //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {
                    String path = "block/" + name.replace(".", "/");
                    ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

                    ExtendedSignalsCore.LOGGER.info("modelled block path: {}", path);

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

    public static <T extends Block> BlockBuilder<T, Registrate> genericCustomSignalBlock(Registrate registrateInstance, String assetType, String nation, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        String properName = name.replaceAll("(\\d+)l", "$1 Light")
                .replaceAll("_post_(\\d+)_?", " (Post $1)")
                .replace('_', ' ');

        BlockBuilder<T, Registrate> block = registrateInstance
                .block("%s.%s".formatted(nation, name), blockCreator)
                .lang("(%s) %s".formatted(SwSignalLang.fromISO639_1(nation), properName))
                .properties(prop -> BlockBehaviour.Properties.of()
                        .destroyTime(1f))
                .blockstate(signalBlockStateModelProvider(assetType, nation, name))
                .item()
                .model(signalItemModelLocator(nation, name))
                .build();

        return block;

    }

    /**
     * Creates and attaches a generic {@link venomized.mods.extendedsignals.core.blockentity.BlockEntityMainSignal} block entity
     *
     * @param registrateInstance
     * @param assetType
     * @param nation
     * @param name
     * @param blockCreator
     * @param <T>
     * @return
     */
    public static <T extends Block> BlockBuilder<T, Registrate> genericMainSignalBlock(Registrate registrateInstance, String assetType, String nation, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        String properName = name.replaceAll("(\\d+)l", "$1 Light")
                .replaceAll("_post_(\\d+)_?", " (Post $1)")
                .replace('_', ' ');

        BlockBuilder<T, Registrate> block = registrateInstance
                .block("%s.%s".formatted(nation, name), blockCreator)
                .lang("(%s) %s".formatted(SwSignalLang.fromISO639_1(nation), properName))
                .properties(prop -> BlockBehaviour.Properties.of()
                        .destroyTime(1f))
                .blockstate(signalBlockStateModelProvider(assetType, nation, name))
                .item()
                .model(signalItemModelLocator(nation, name))
                .build();

        ExtendedSignalsCoreBlockEntities.validMainSignalBlock(block.asSupplier());

        return block;

    }

    /**
     * Creates and attaches a generic {@link venomized.mods.extendedsignals.core.blockentity.BlockEntityCombinedSignal} block entity
     *
     * @param registrateInstance
     * @param assetType
     * @param nation
     * @param name
     * @param blockCreator
     * @param <T>
     * @return
     */
    public static <T extends Block> BlockBuilder<T, Registrate> genericCombinedSignalBlock(Registrate registrateInstance, String assetType, String nation, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        String properName = name.replaceAll("(\\d+)l", "$1 Light")
                .replaceAll("_post_(\\d+)_?", " (Post $1)")
                .replace('_', ' ');

        BlockBuilder<T, Registrate> block = registrateInstance
                .block("%s.%s".formatted(nation, name), blockCreator)
                .lang("(%s) %s".formatted(SwSignalLang.fromISO639_1(nation), properName))
                .properties(prop -> BlockBehaviour.Properties.of()
                        .destroyTime(1f))
                .blockstate(signalBlockStateModelProvider(assetType, nation, name))
                .item()
                .model(signalItemModelLocator(nation, name))
                .build();

        ExtendedSignalsCoreBlockEntities.validCombinedSignalBlock(block.asSupplier());

        return block;

    }

    private static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> signalBlockStateModelProvider(final String assetType, final String nation, final String name) {
        return (blockTDataGenContext, registrateBlockstateProvider) -> {
            //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {
            String path = "block/%s/%s/%s".formatted(assetType, nation, name);
            ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

            if (!registrateBlockstateProvider.models().existingFileHelper.exists(
                    loc, PackType.CLIENT_RESOURCES, ".json", "models")) {
                return;
            }

            registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
                    .forAllStates(blockState -> new ConfiguredModel[]{ConfiguredModel.builder()
                            .modelFile(registrateBlockstateProvider.models()
                                    .getExistingFile(loc))
                            .buildLast()});
            //}
        };
    }

    private static NonNullBiConsumer<DataGenContext<Item, BlockItem>, RegistrateItemModelProvider> signalItemModelLocator(String nation, String name) {
        return (ctx, prov) -> {
            if (prov.existingFileHelper.exists(
                    prov.modLoc("item/" + "%s.%s".formatted(nation, name)), PackType.CLIENT_RESOURCES, ".json",
                    "models"
            )) {
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
        };
    }

    public static <T extends BlockEntity> BlockEntityBuilder<T, Registrate> simpleBlockEntity(Registrate registrateInstance, String beName, BlockEntityBuilder.BlockEntityFactory<T> beFactory, NonNullSupplier<? extends Block>... validBlocks) {
        return registrateInstance
                .blockEntity(beName, beFactory)
                .validBlocks(validBlocks);
    }
}
