package venomized.mods.extendedsignals.core.registry;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Consumer;

public record BlockBlockEntityCoBuilder<L extends net.minecraft.world.level.block.Block, R extends BlockEntity, P>(
        BlockBuilder<L, P> left,
        BlockEntityBuilder<R, P> right) implements Pair<BlockBuilder<L, P>, BlockEntityBuilder<R, P>> {
    public BlockBlockEntityCoEntry<L, R> register() {
        return new BlockBlockEntityCoEntry<>(left().register(), right().register());
    }

    public BlockBlockEntityCoBuilder<L, R, P> actOnLeft(Consumer<BlockBuilder<L, P>> action) {
        action.accept(left());
        return this;
    }

    public BlockBlockEntityCoBuilder<L, R, P> actOnRight(Consumer<BlockEntityBuilder<R, P>> action) {
        action.accept(right());
        return this;
    }
}
