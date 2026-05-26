package venomized.mods.extendedsignals.core;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntity;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.SyncSignalStatesPacket;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ExtendedSignalsCore.MOD_ID)
public class EventHandler {
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

        ExtendedSignalsNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), syncPacket);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        if (e.getLevel().isClientSide())
            return;

        BlockEntity blockEntity = e.getLevel().getBlockEntity(e.getPos());
        if (blockEntity instanceof CoreBlockEntity blockEntitySignal) {
            blockEntitySignal.onBlockDestroyed(e.getPlayer());
        }
    }
}
