package venomized.mods.extendedsignals.de.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;
import venomized.mods.extendedsignals.de.GermanySounds;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityCrossingLight extends BlockEntityCrossingObject {
    private int tick;

    public BlockEntityCrossingLight(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public static <S extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, S s) {
        BlockEntityCrossingLight be = (BlockEntityCrossingLight) s;

        if (!be.isActive()) {
            be.tick = 0;
            return;
        }

        if (be.tick++ == 0) {
            level.playSound(null, blockPos, GermanySounds.CROSSING_E_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        be.tick = be.tick % 40;
    }
}
