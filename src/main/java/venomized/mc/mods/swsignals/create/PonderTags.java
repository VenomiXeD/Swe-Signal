package venomized.mc.mods.swsignals.create;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.se.SeBlocks;
import venomized.mc.mods.swsignals.item.SwItems;

public class PonderTags {
	public static final PonderTag DWARF_SIGNALS = new PonderTag(
			SwSignal.modLoc("dwarf_signals")
	)
			.item(SwItems.ITEM_DWARF_SIGNAL.get(), true, false);

	public static void register() {
		PonderRegistry.TAGS.forTag(DWARF_SIGNALS)
				.add(SeBlocks.BLOCK_MODERN_DWARF_SIGNAL.get());
	}
}
