package venomized.mods.extendedsignals.core.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.level.block.entity.BlockEntity;

public record BlockBlockEntityCoEntry<L extends net.minecraft.world.level.block.Block, R extends BlockEntity>(
        BlockEntry<L> left, BlockEntityEntry<R> right) implements Pair<BlockEntry<L>, BlockEntityEntry<R>> {

}
