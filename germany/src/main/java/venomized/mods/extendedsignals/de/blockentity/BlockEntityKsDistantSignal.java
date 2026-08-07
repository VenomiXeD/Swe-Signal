package venomized.mods.extendedsignals.de.blockentity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.signalling.KsDistantSignalAspect;

import java.util.List;

public class BlockEntityKsDistantSignal extends BlockEntitySignal<ISignalAspect> {
    public BlockEntityKsDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(3.5d / 16d, 93 / 16f, -8.4 / 16d, 1.75f, 1.75f, 0f),
                new SignalLightPlacement(2.5d / 16d, 87.75d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0f),
                new SignalLightPlacement(-2.5d / 16d, 87.75 / 16f, -8.55 / 16d, 2.75f, 2.75f, 0f)
        };
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull ISignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (state.getNextState() == null || !state.getNextState().isProceed()) {
            return KsDistantSignalAspect.EXPECT_STOP;
        }

        if (state.getNextState().getMaxProceedSpeed() >= state.getMaxProceedSpeed()) {
            return KsDistantSignalAspect.EXPECT_PROCEED;
        }

        return KsDistantSignalAspect.EXPECT_REDUCED_SPEED;
    }
}
