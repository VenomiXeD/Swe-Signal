package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import venomized.mc.mods.swsignals.SwSignal;

public class ModelDataGenerator extends BlockModelProvider {
	public ModelDataGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, SwSignal.MOD_ID, existingFileHelper);
	}

	/**
	 *
	 */
	@Override
	protected void registerModels() {
		// this.getBuilder("light").customLoader(ObjModelBuilder::begin)
		// 		.modelLocation(ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, "models/block/light.obj"))
		// 		.emissiveAmbient(true)
		// 		.shadeQuads(false)
		// 		.flipV(true)
		// 		.end();
		// this.getBuilder("sw_5l_signal_post_1970").customLoader(ObjModelBuilder::begin)
		// 		.modelLocation(ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, "models/block/sw_5l_signal_post_1970.obj"))
		// 		.end();
	}
}
