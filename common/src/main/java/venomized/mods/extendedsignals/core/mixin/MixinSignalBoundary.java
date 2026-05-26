package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.IRawSignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.SignalBoundaryConfiguration;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary, IRawSignalStateEvaluator {
    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Shadow
    public Couple<UUID> groups;

    @Unique
    public Couple<ResourceLocation> extendedSignals$stateRemapperIDs = Couple.create(
            SignalStateRemapper.NONE.getId(),
            SignalStateRemapper.NONE.getId()
    );

    /**
     * @return
     */
    @Override
    public SignalBoundaryConfiguration getPositiveSignalBoundaryConfiguration() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public SignalBoundaryConfiguration getNegativeSignalBoundaryConfiguration() {
        return null;
    }

    /**
     * @param upcomingSignal
     * @return
     */
    @Override
    public RawSignalState computeRawSignalState(Direction.AxisDirection axisDirection, RawSignalState upcomingSignal, Train train) {
        boolean front = this.cachedStates.get(axisDirection == Direction.AxisDirection.POSITIVE) == SignalBlockEntity.SignalState.RED;
        if (front && !train.reservedSignalBlocks.contains(this.groups.get(axisDirection == Direction.AxisDirection.POSITIVE)))
            return new RawSignalState().setReserved(false);

        RawSignalState state = new RawSignalState()
                .setProceed(true)
                .setReserved(true);

        ResourceLocation mapperId = extendedSignals$stateRemapperIDs.get(front);
        if (mapperId == null)
            return state;

        return SignalStateRemapper.transform(mapperId, state);
    }

    /**
     * @param front
     * @param mapper
     */
    @Override
    public void setMapper(boolean front, SignalStateRemapper mapper) {
        extendedSignals$stateRemapperIDs.set(front, mapper.getId());
    }

    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"))
    public void extendedSignals$read(CompoundTag nbt, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        extendedSignals$stateRemapperIDs.setFirst(NBTHelper.readResourceLocation(nbt, "mapper0"));
        extendedSignals$stateRemapperIDs.setSecond(NBTHelper.readResourceLocation(nbt, "mapper1"));
    }


    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"))
    public void extendedSignals$write(CompoundTag nbt, DimensionPalette dimensions, CallbackInfo ci) {
        NBTHelper.writeResourceLocation(nbt, "mapper0", extendedSignals$stateRemapperIDs.getFirst());
        NBTHelper.writeResourceLocation(nbt, "mapper1", extendedSignals$stateRemapperIDs.getSecond());
    }

    /**
     * @return
     */
    @Override
    public boolean skipChaining() {
        return false;
    }
}
