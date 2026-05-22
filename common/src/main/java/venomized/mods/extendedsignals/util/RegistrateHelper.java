package venomized.mods.extendedsignals.util;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

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

    public static <T extends Block> BlockBuilder<T, Registrate> signalBlock(Registrate registrateInstance, String assetType, String nation, String name, NonNullFunction<BlockBehaviour.Properties, T> blockCreator) {
        String properName = name.replaceAll("(\\d+)l", "$1 Light")
                .replaceAll("_post_(\\d+)_?", " (Post $1)")
                .replace('_', ' ');

        return registrateInstance
                .block("%s.%s".formatted(nation, name), blockCreator)
                .lang("(%s) %s".formatted(venomized.mods.extendedsignals.data.SwSignalLang.fromISO639_1(nation), properName))
                .properties(prop -> BlockBehaviour.Properties.of().destroyTime(1f))
                .blockstate((blockTDataGenContext, registrateBlockstateProvider) -> {
                    //if (blockTDataGenContext.get() instanceof Sw45DegreeBlock) {
                    String path = "block/%s/%s/%s".formatted(assetType, nation, name);
                    ResourceLocation loc = registrateBlockstateProvider.modLoc(path);

                    ExtendedSignalsCore.LOGGER.info("modelled block path: {}", path);

                    if (!registrateBlockstateProvider.models().existingFileHelper.exists(loc, PackType.CLIENT_RESOURCES, ".json", "models")) {
                        return;
                    }

                    registrateBlockstateProvider.getVariantBuilder(blockTDataGenContext.get())
                            .forAllStates(blockState -> new ConfiguredModel[]{ConfiguredModel.builder().modelFile(registrateBlockstateProvider.models().getExistingFile(loc))
                                    .buildLast()});
                    //}
                })
                .item()
                .model((ctx, prov) -> {
                    if (prov.existingFileHelper.exists(prov.modLoc("item/" + "%s.%s".formatted(nation, name)), PackType.CLIENT_RESOURCES, ".json", "models")) {
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
    }
}
