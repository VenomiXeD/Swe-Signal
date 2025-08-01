package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntityBase;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityDwarfSignal;

public class BlockModernDwarfSignal extends BlockSignal {
	public BlockModernDwarfSignal(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return SwBlockEntities.BE_DWARF_SIGNAL.create(pPos, pState);
	}
}
