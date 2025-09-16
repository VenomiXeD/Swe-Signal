package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import venomized.mc.mods.swsignals.SwSignal;

public class BlockStateDataGenerator extends BlockStateProvider {
    public BlockStateDataGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SwSignal.MOD_ID, exFileHelper);
    }

    private ConfiguredModel[] justABlockModel(ResourceLocation res) {
        return ConfiguredModel.builder().modelFile(new ModelFile.ExistingModelFile(res, this.models().existingFileHelper)).build();
    }

    @Override
    protected void registerStatesAndModels() {
        // this.simpleBlock(SeBlocks.BLOCK_SIGNAL_BOX.get(), this.models().cubeAll("signal_box", modLoc("block/signals/se/signal_box")));

        // SwBlocks.BLOCKS.getEntries().forEach(
        // 		block -> {
        // 			if (block.get() instanceof Sw45DegreeBlock block45deg) {
        // 				this.getVariantBuilder(
        // 						block.get()).forAllStates(e -> justABlockModel(modLoc("block/" + block.getId().getPath().replace(".", "/")))
        // 				);
        // 			}
        // 		}
        // );

        // this.getVariantBuilder(SwBlocks.BLOCK_U_SIGN.get()).forAllStates(
        // 		e->ConfiguredModel.builder().modelFile(new ModelFile.ExistingModelFile(modLoc("block/se/se_u_sign"), this.models().existingFileHelper)).build()
        // );
    }
}
