package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.AllSounds;

public interface ITickingEntity<T> {
	default void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
	}
}
