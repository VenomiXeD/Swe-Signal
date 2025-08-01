package venomized.mc.mods.swsignals.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntityATCController;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.network.Networking;
import venomized.mc.mods.swsignals.network.UpdateATCEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ATCController extends SingleBlockEntityEdgePoint {

	public static final EdgePointType<ATCController> ATC = EdgePointType.register(
			SwSignal.modLoc("atc"), ATCController::new
	);

	public ATCController() {
	}


	/**
	 * Executed when a train runs over.
	 *
	 * @param train
	 */
	public void onATCAction(Train train) {
		Optional<UUID> controllingPlayer = train.carriages.stream().map(e -> e.anyAvailableEntity().getControllingPlayer().orElseGet(() -> null)).filter(e -> !Objects.isNull(e)).findFirst();
		Level level = train.carriages.get(0).anyAvailableEntity().level();

		Optional<BlockEntityATCController> blockEntity = level.getBlockEntity(this.getBlockEntityPos(), SwBlockEntities.BE_ATC_CONTROLLER.get());
		blockEntity.ifPresent(blockEntityATCController -> {
			// if any player is controlling
			if (controllingPlayer.isPresent()) {
				Networking.CHANNEL.send(
						PacketDistributor.PLAYER.with(() -> (ServerPlayer) level.getPlayerByUUID(controllingPlayer.get())),
						new UpdateATCEvent(0.5f)
				);
			}
			// if AI is controlling it (likely)
			else {
				train.throttle = 1.0f;
			}
		});
	}
}
