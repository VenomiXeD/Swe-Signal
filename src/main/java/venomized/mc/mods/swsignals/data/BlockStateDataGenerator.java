package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import venomized.mc.mods.swsignals.SwSignal;

public class BlockStateDataGenerator extends BlockStateProvider {
	public BlockStateDataGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, SwSignal.MOD_ID, exFileHelper);
	}

	/**
	 *
	 */
	@Override
	protected void registerStatesAndModels() {
	}
}
