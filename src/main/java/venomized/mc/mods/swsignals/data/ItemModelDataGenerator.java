package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.item.SwItems;

public class ItemModelDataGenerator extends ItemModelProvider {
	public ItemModelDataGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, SwSignal.MOD_ID, existingFileHelper);
	}

	/**
	 *
	 */
	@Override
	protected void registerModels() {
		this.basicItem(SwItems.ITEM_U_SIGN.get())
				.override()
				.model(new ModelFile.ExistingModelFile(modLoc("block/sw_u_sign"), existingFileHelper))
				.end();
	}
}
