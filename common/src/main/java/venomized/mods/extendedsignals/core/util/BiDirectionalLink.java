package venomized.mods.extendedsignals.core.util;

import net.minecraft.world.level.block.entity.BlockEntity;

public class BiDirectionalLink<F extends BlockEntity, S extends BlockEntity> {
    private BlockEntityReference<F> first;
    private BlockEntityReference<S> second;

    public void dissolve() {

    }
}
