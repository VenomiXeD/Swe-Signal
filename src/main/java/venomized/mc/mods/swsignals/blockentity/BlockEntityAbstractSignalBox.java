package venomized.mc.mods.swsignals.blockentity;

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
import venomized.mc.mods.swsignals.util.BlockEntityReference;

import java.util.Optional;

public abstract class BlockEntityAbstractSignalBox extends SwBlockEntity implements ISignalTunerBindable {
    private static final String TAG_CREATE_SIGNAL_LINK = "linked_signal_group";

    private final BlockEntityReference<SignalBlockEntity> refCreateSignalBox = new BlockEntityReference<>(SignalBlockEntity.class, TAG_CREATE_SIGNAL_LINK);

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

    private void validateAndUpdateSignalEdgeGroupReference() {
        SignalBlockEntity reference = refCreateSignalBox.getReference(this.level);
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

    /**
     * Called on the target block entity;
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity
     * @param mode
     * @return
     */
    @Override
    public Pair<InteractionResult, MutableComponent> onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
        switch (mode) {
            case CONNECT:
                return sourceBlockEntity.map(blockEntity -> {
                    if (blockEntity instanceof SignalBlockEntity sbe) {
                        refCreateSignalBox.newTarget(sbe);
                        validateAndUpdateSignalEdgeGroupReference();
                        return Pair.of(
                                InteractionResult.SUCCESS,
                                Component.literal("yup")
                        );
                    }
                    return null;
                }).orElse(Pair.of(InteractionResult.PASS, Component.literal("bleh")));
        }
        return Pair.of(
                InteractionResult.PASS, Component.literal("nothing")
        );
    }
}
