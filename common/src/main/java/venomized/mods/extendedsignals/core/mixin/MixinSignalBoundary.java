package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.IRawSignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.SignalBoundaryConfiguration;
import venomized.mods.extendedsignals.core.signalling.ISignalStateBoundaryTransformer;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.Objects;
import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary<SignalBoundary>, IRawSignalStateEvaluator, ISignalStateBoundaryTransformer {
    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Shadow
    public Couple<UUID> groups;

    @Shadow
    public abstract void invalidate(LevelAccessor level);

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
    public RawSignalState computeRawSignalState(boolean primary, RawSignalState upcomingSignal, Train train) {
        boolean isRed = this.cachedStates.get(primary) == SignalBlockEntity.SignalState.RED;
        if (isRed && !train.reservedSignalBlocks.contains(this.groups.get(primary)))
            return new RawSignalState().setReserved(false);

        RawSignalState state = new RawSignalState()
                .setProceed(true)
                .setReserved(true);

        return state;
    }

    /**
     * @param front
     * @param mapper
     */
    @Override
    public void setMapper(boolean front, SignalStateRemapper mapper) {
        extendedSignals$stateRemapperIDs.set(front, Objects.requireNonNullElse(mapper, SignalStateRemapper.NONE).getId());
    }

    @Override
    public RawSignalState transformSignalState(boolean front, RawSignalState state) {
        ResourceLocation mapperId = extendedSignals$stateRemapperIDs.get(front);
        return SignalStateRemapper.getMappers().get(mapperId).remap(state);
    }

    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/signal/TrackEdgePoint;read(Lnet/minecraft/nbt/CompoundTag;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
                    shift = At.Shift.AFTER
            )
    )
    public void extendedSignals$read(CompoundTag nbt, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        extendedSignals$stateRemapperIDs.setFirst(NBTHelper.readResourceLocation(nbt, "mapper0"));
        extendedSignals$stateRemapperIDs.setSecond(NBTHelper.readResourceLocation(nbt, "mapper1"));
    }


    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("HEAD"))
    public void write(CompoundTag nbt, DimensionPalette dimensions, CallbackInfo ci) {
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
