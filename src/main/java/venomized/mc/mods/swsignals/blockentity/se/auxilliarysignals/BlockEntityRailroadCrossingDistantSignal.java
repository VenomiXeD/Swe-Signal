package venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingObject;

public class BlockEntityRailroadCrossingDistantSignal extends BlockEntityRailroadCrossingObject {
    public float lightLevel = 0;
    private int tick;

    public BlockEntityRailroadCrossingDistantSignal(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public boolean blink() {
        return tick > 10;
    }

    public void tick(Level level, BlockPos pos, BlockState state, Object blockEntity) {
        this.tick = (this.tick + 1) % 20;
    }
}
