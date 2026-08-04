package venomized.mods.extendedsignals.core;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntity;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundSyncSignalStatesPacket;


public class Events {
    // It's weird but it should work
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent e) {
        ExtendedSignals.EXTENDED_SIGNAL_SERVER_CACHE = ServerSignalNetworkCache.get(e.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer serverPlayer)) return;

        ClientBoundSyncSignalStatesPacket syncPacket = new ClientBoundSyncSignalStatesPacket(
                ExtendedSignals.serverNetworkCache().signalStates()
        );

        PacketDistributor.sendToPlayer(serverPlayer, syncPacket);
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent e) {
        BlockEntity blockEntity = e.getLevel().getBlockEntity(e.getPos());
        if (blockEntity instanceof CoreBlockEntity blockEntitySignal) {
            blockEntitySignal.onBlockDestroyed(e.getPlayer());
        }
    }
}
