package venomized.mods.extendedsignals.se.blockentity;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;

public class BlockEntityMainSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntityMainSignal {
    public BlockEntityMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @param signalBlockEntity
     * @return
     */
    @Override
    public IMainSignalAspect interpret(@Nullable BlockEntitySignal<?> signalBlockEntity) {
        if (rawState.isProceed()) {
            return MainSignalAspects.PROCEED_80;
        }

        return MainSignalAspects.STOP;
    }

    @RequiredArgsConstructor
    public enum MainSignalAspects implements IMainSignalAspect {
        PROCEED_80(
                new Vector3f(0, 1, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 0)
        ),
        STOP(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 0, 0)
        );

        private final Vector3f l0;
        private final Vector3f l1;
        private final Vector3f l2;

        /**
         * @param totalTicksForBlockEntity
         * @param states
         */
        @Override
        public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
            states[0].setColorDirect(l0.x, l0.y, l0.z);
            states[1].setColorDirect(l1.x, l1.y, l1.z);
            if (states.length == 3)
                states[2].setColorDirect(l2.x, l2.y, l2.z);
        }
    }
}
