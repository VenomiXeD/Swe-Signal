package venomized.mods.extendedsignals.core.blockentity;

import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.util.BlockEntityReference;

import java.util.Optional;

public abstract class BlockEntityAbstractSignalBox extends ExtendedSignalsCoreBlockEntity implements ISignalTunerToolable {
    private static final String TAG_CREATE_SIGNAL_NBT_KEY = "linked_signal_group";

    private final BlockEntityReference<SignalBlockEntity> refCreateSignalBox = new BlockEntityReference<>(SignalBlockEntity.class, TAG_CREATE_SIGNAL_NBT_KEY);

    /**
     * @param pPos
     * @param pBlockState
     */
    public BlockEntityAbstractSignalBox(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * @param pTag
     */
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        refCreateSignalBox.toNBT(pTag);
    }

    /**
     * @param pTag
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        refCreateSignalBox.fromNBT(pTag);
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for
     * initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in
     * {@link handleUpdateTag}
     */
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag pTag = super.getUpdateTag();
        this.saveAdditional(pTag);
        return pTag;
    }

    private boolean connectCreateTrainSignal(SignalBlockEntity pBlockEntity) {
        boolean success = refCreateSignalBox.newTarget(pBlockEntity);

        if (success) {
            SignalBlockEntity reference = refCreateSignalBox.getReference(this).orElse(null);
            TrackGraph graph = reference.edgePoint.determineGraphLocation().graph;
            TrackEdge connection = graph.getConnection(
                    reference.edgePoint.getEdgePoint().edgeLocation
                            .map(reference.edgePoint.determineGraphLocation().graph::locateNode)
            );
        }

        return success;
    }

    /**
     * Called on the target block entity;
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity
     * @param mode
     * @return
     */
    @Override
    public Pair<InteractionResult, MutableComponent> sourceBindingToReader(Optional<ISignalTunerToolable> sourceBlockEntity, SignalTunerMode mode) {
        switch (mode) {
            case CONNECT:
                return sourceBlockEntity.map(blockEntity -> {
                    if (blockEntity instanceof SignalBlockEntity sbe) {
                        return connectCreateTrainSignal(sbe) ? Pair.of(
                                InteractionResult.SUCCESS,
                                Component.literal("yup")) : Pair.of(InteractionResult.FAIL, Component.literal("bruh"));
                    }
                    return null;
                }).orElse(Pair.of(InteractionResult.PASS, Component.literal("bleh")));
        }
        return Pair.of(
                InteractionResult.PASS, Component.literal("nothing")
        );
    }
}
