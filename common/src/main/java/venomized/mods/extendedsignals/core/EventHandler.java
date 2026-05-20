package venomized.mods.extendedsignals.core;


import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import venomized.mods.extendedsignals.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.network.packets.SyncSignalStatesPacket;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid =  ExtendedSignalsCore.MOD_ID)
public class EventHandler {
    private static SignalBlockEntity firstSignal;
    private static SignalBlockEntity secondSignal;

    // It's weird but it should work
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent e) {
        ExtendedSignalsCore.EXTENDED_SIGNAL_SERVER_CACHE = ServerSignalNetworkCache.get(e.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer serverPlayer)) return;

        SyncSignalStatesPacket syncPacket = new SyncSignalStatesPacket(
                ExtendedSignalsCore.serverNetworkCache().signalStates()
        );

        ExtendedSignalsNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(()->serverPlayer), syncPacket);
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(InteractionHand.OFF_HAND))
            return;
        if (!event.getLevel().isClientSide()) {
            if (event.getLevel().getBlockEntity(event.getHitVec().getBlockPos()) instanceof SignalBlockEntity sbe) {
                // if (firstSignal == null) {
                //     System.out.println("First signal selected");
                //     firstSignal = sbe;
                // }

                // UUID forward = SignalUtilities.getGroupForSignalForwardDirection(firstSignal);
                // UUID backward = SignalUtilities.getGroupForSignalBackwardDirection(firstSignal);
                // // SignalEdgeGroup orDefault = Create.RAILWAYS.signalEdgeGroups.getOrDefault(group, null);
                // // int trainsOnThisBlock = orDefault.trains.size();
                // SignalUtilities.DistantSignalEdgeGroup id = SignalUtilities.recursiveGetDistantSignalEdgeGroup(forward, backward, 2);
                // System.out.println(id);
                // if (id != null) {
                // 	SignalEdgeGroup signalEdgeGroupById = SignalUtilities.getSignalEdgeGroupById(id.distant(), true);
                // 	if (signalEdgeGroupById != null) {
                // 		signalEdgeGroupById.color = EdgeGroupColor.values()[event.getLevel().random.nextInt(EdgeGroupColor.values().length)];
                // 	}
                // }

                // if (orDefault != null) {
                // 	Collection<TrackGraph> values = CreateClient.RAILWAYS.trackNetworks.values();
                // 	for (TrackGraph value : values) {
                // 		Collection<SignalBoundary> points = value.getPoints(EdgePointType.SIGNAL);
                // 		for (SignalBoundary point : points) {
                // 			if (point.groups.either(u -> u.equals(group))) {
                // 				for (SignalEdgeGroup signalEdgeGroup : CreateClient.RAILWAYS.signalEdgeGroups.values()) {
                // 					if (point.groups.either(e -> e.equals(signalEdgeGroup.id))) {
                // 						if (!signalEdgeGroup.id.equals((group))) {
                // 							signalEdgeGroup.color = EdgeGroupColor.values()[event.getLevel().random.nextInt(EdgeGroupColor.values().length)];
                // 							System.out.println(signalEdgeGroup.id);
                // 						}
//
                // 					}
                // 				}
                // 			}
                // 		}
                // 	}
                // 	// orDefault.color = EdgeGroupColor.values()[event.getLevel().random.nextInt(EdgeGroupColor.values().length)];


                // System.out.println("trains count on the coming block: " + trainsOnThisBlock);
                firstSignal = null;
				/*
				if (secondSignal == null) {
					System.out.println("Second signal selected");
					secondSignal = sbe;
				}

				SignalEdgeGroup firstSignalEdge = null;
				for (boolean b : Iterate.falseAndTrue) {
					if (firstSignalEdge == null) {
						firstSignalEdge = CreateClient.RAILWAYS.signalEdgeGroups.getOrDefault(firstSignal.edgePoint.getEdgePoint().groups.get(b), null);
					}
				}

				SignalEdgeGroup secondSignalEdge = null;
				for (boolean b : Iterate.falseAndTrue) {
					if (secondSignalEdge == null) {
						secondSignalEdge = CreateClient.RAILWAYS.signalEdgeGroups.getOrDefault(secondSignal.edgePoint.getEdgePoint().groups.get(b), null);
					}
				}

				if (firstSignalEdge != null && secondSignalEdge != null) {
					UUID group = SignalUtilities.getGroupForSignalWithDirection(firstSignal);
					SignalEdgeGroup orDefault = CreateClient.RAILWAYS.signalEdgeGroups.getOrDefault(group, null);
					if (orDefault != null) {
						orDefault.color = EdgeGroupColor.values()[event.getLevel().random.nextInt(EdgeGroupColor.values().length)];
					}

					// firstSignalEdge.color = EdgeGroupColor.values()[event.getLevel().random.nextInt(EdgeGroupColor.values().length)];
					// secondSignalEdge.color = EdgeGroupColor.values()[event.getLevel().random.nextInt(EdgeGroupColor.values().length)];

					System.out.println("t");

					// CreateClient.RAILWAYS.signalEdgeGroups.values().forEach(e->e.color = EdgeGroupColor.RED);
				}
				firstSignal = null;
				secondSignal = null;
			}*/
            }
        }
    }
}
