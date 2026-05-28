package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;

@Mixin(value = SignalBlockEntity.class, remap = false)
public abstract class MixinCreateTrainSignal implements ISignalTunerToolable, ISignalBoundaryReferenceProvider {
    @Shadow
    public TrackTargetingBehaviour<SignalBoundary> edgePoint;

    @Override
    public boolean isReader() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public TrackTargetingBehaviour<?> getTrackTargetingBehavior() {
        return this.edgePoint;
    }

    /**
     * @param mode
     * @param context
     * @return
     */
    @Override
    public InteractionResult onSignalToolInteract(SignalTunerMode mode, UseOnContext context) {
        if (context.getLevel().isClientSide())
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());

        if (mode == SignalTunerMode.CONFIGURE) {
            IExtendedSignalBoundary<SignalBoundary> boundary = (IExtendedSignalBoundary<SignalBoundary>) edgePoint.getEdgePoint();
            boolean primary = edgePoint.getTargetDirection() == Direction.AxisDirection.POSITIVE;
            boolean old = boundary.getChainingSkipped(primary);
            boundary.setChainingSkipped(primary, !old);

            context.getPlayer()
                    .sendSystemMessage(Component.translatable(
                            "message.extendedsignals.createsignal.interact.chaining", !old
                    ));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
