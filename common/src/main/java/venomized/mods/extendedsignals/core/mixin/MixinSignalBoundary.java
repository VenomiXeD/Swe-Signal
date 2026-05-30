package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.IRawSignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.SignalBoundaryConfiguration;
import venomized.mods.extendedsignals.core.signalling.ISignalStateBoundaryTransformer;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.Objects;
import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary<SignalBoundary>, IRawSignalStateEvaluator, ISignalStateBoundaryTransformer {
    private static final String TAG_MAPPER_NAME = "mapper";
    private static final String TAG_SKIP_CHAIN_CONFIG_NAME = "chaining";

    @Unique
    public Couple<SignalBlockEntity.SignalState> extendedSignals$lastCachedState;

    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Shadow
    public Couple<UUID> groups;
    @Unique
    private Couple<ResourceLocation> extendedSignals$stateRemapperIDs;
    @Unique
    private Couple<Boolean> extendedSignals$skipChainingConfiguration;

    @Shadow
    public abstract void invalidate(LevelAccessor level);

    @Shadow
    public abstract boolean isForcedRed(boolean primary);

    @Shadow
    public abstract boolean isForcedRed(TrackNode side);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void extendedSignals$Ctor(CallbackInfo ci) {
        extendedSignals$lastCachedState = Couple.create(() -> SignalBlockEntity.SignalState.INVALID);
        extendedSignals$skipChainingConfiguration = Couple.create(false, false);
        extendedSignals$stateRemapperIDs = Couple.create(SignalStateRemapper.NONE.getId(), SignalStateRemapper.NONE.getId());


    }

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
    public SignalStateNode computeRawSignalState(Direction.AxisDirection direction, SignalStateNode upcomingSignal, Train train) {
        boolean primary = direction == Direction.AxisDirection.POSITIVE;
        SignalEdgeGroup entering = Create.RAILWAYS.signalEdgeGroups.get(groups.get(primary));

        if (isForcedRed(primary))
            return SignalStateNode.STOP;

        if (entering.isOccupiedUnless((SignalBoundary) (Object) this) &&
                        entering.isOccupiedUnless(train)) {
            return SignalStateNode.STOP;
        }

        return new SignalStateNode().setProceed(true).setReserved(true);
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
    public SignalStateNode transformSignalState(Direction.AxisDirection direction, SignalStateNode state) {
        ResourceLocation mapperId = extendedSignals$stateRemapperIDs.get(direction == Direction.AxisDirection.POSITIVE);
        return SignalStateRemapper.getMappers().getOrDefault(mapperId, SignalStateRemapper.NONE).remap(state);
    }

    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/signal/TrackEdgePoint;read(Lnet/minecraft/nbt/CompoundTag;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
                    shift = At.Shift.AFTER
            )
    )
    public void extendedSignals$read(CompoundTag nbt, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        extendedSignals$stateRemapperIDs.setFirst(NBTHelper.readResourceLocation(nbt, TAG_MAPPER_NAME + "0"));
        extendedSignals$stateRemapperIDs.setSecond(NBTHelper.readResourceLocation(nbt, TAG_MAPPER_NAME + "1"));
        for (boolean side : Iterate.trueAndFalse) {
            extendedSignals$skipChainingConfiguration.set(
                    side, nbt.getBoolean(TAG_SKIP_CHAIN_CONFIG_NAME + (side ? "0" : "1"))
            );
        }
    }


    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("HEAD"))
    public void write(CompoundTag nbt, DimensionPalette dimensions, CallbackInfo ci) {
        NBTHelper.writeResourceLocation(nbt, TAG_MAPPER_NAME + "0", extendedSignals$stateRemapperIDs.get(false));
        NBTHelper.writeResourceLocation(nbt, TAG_MAPPER_NAME + "1", extendedSignals$stateRemapperIDs.get(true));

        for (boolean side : Iterate.trueAndFalse) {
            nbt.putBoolean(TAG_SKIP_CHAIN_CONFIG_NAME + (side ? "0" : "1"), extendedSignals$skipChainingConfiguration.get(side));
        }
    }

    /**
     * @return
     */
    @Override
    public boolean doSkipChaining(Direction.AxisDirection direction, Train train) {
        return extendedSignals$skipChainingConfiguration.get(direction == Direction.AxisDirection.POSITIVE);
    }

    /**
     * @param front
     * @param skipChaining
     */
    @Override
    public void setChainingSkipped(boolean front, boolean skipChaining) {
        extendedSignals$skipChainingConfiguration.set(front, skipChaining);
    }

    /**
     * @param front
     * @return
     */
    @Override
    public boolean getChainingSkipped(boolean front) {
        return extendedSignals$skipChainingConfiguration.get(front);
    }

    // @WrapOperation(
    //         method = "tickState",
    //         at = @At(
    //                 value = "INVOKE",
    //                 target = "Lnet/createmod/catnip/data/Couple;set(ZLjava/lang/Object;)V"
    //         )
    // )
    // public <T> void extendedSignals$tickStateCachedStatesUpdated(Couple<T> instance, boolean first, T value, Operation<Void> original) {
    //     original.call(instance, first, value);
//
    //     SignalStateNode node = ExtendedSignalsCore.serverNetworkCache().getSignalState(pointId());
    //     if (!node.isValid())
    //         return;
//
    //     if (extendedSignals$lastCachedState.get(first) == value)
    //         return;
//
    //     SignalBlockEntity.SignalState state = (SignalBlockEntity.SignalState) value;
    //     extendedSignals$lastCachedState.set(first, state);
    //     node.setCreateSignalState(first, state);
    // }
    /**
     * @return
     */
    @Override
    public UUID pointId() {
        return this.getId();
    }
}
