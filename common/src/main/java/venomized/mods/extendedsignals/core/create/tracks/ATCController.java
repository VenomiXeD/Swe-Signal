package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityATCController;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.UpdateATCEventPacket;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ATCController extends SingleBlockEntityEdgePoint {
    public static final EdgePointType<ATCController> ATC = EdgePointType.register(
            ExtendedSignalsCore.res("balise"), ATCController::new
    );

    public ATCController() {
    }


    /**
     * Executed when a train runs over.
     *
     * @param train
     */
    public void onATCAction(Train train) {
        Optional<UUID> controllingPlayer = train
                .carriages
                .stream()
                .filter(carriage -> Objects.nonNull(carriage.anyAvailableEntity()))
                .map(c -> c.anyAvailableEntity()
                        .getControllingPlayer())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        Optional<Level> level = Optional.ofNullable(train.carriages.get(0).anyAvailableEntity()).map(Entity::level);

        level.ifPresent(l -> {
            Optional<BlockEntityATCController> blockEntity = l.getBlockEntity(this.getBlockEntityPos(), CoreBlockEntities.ATC_CONTROLLER.get());
            blockEntity.ifPresent(blockEntityATCController -> {
                // if any player is controlling
                if (controllingPlayer.isPresent()) {
                    ExtendedSignalsNetworking.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> (ServerPlayer) l.getPlayerByUUID(controllingPlayer.get())),
                            new UpdateATCEventPacket(0.5f)
                    );
                }
                // if AI is controlling it
                else {
                    train.throttle = 1.0f;
                }
            });
        });
    }
}
