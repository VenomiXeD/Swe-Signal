package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.BlockUSign;

public class ItemModelDataGenerator extends ItemModelProvider {
	public ItemModelDataGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, SwSignal.MOD_ID, existingFileHelper);
	}

	/**
	 *
	 */
	@Override
	protected void registerModels() {
		this.sign(BlockUSign.BLOCK_NAME,modLoc("block/sw_u_sign_plate"));
	}
}
