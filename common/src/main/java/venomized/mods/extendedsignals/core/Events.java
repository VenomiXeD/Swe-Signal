package venomized.mods.extendedsignals.core;


import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntity;
import venomized.mods.extendedsignals.core.client.KeyMappings;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundSyncSignalStatesPacket;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ExtendedSignalsCore.MOD_ID)
public class Events {
    // It's weird but it should work
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent e) {
        ExtendedSignalsCore.EXTENDED_SIGNAL_SERVER_CACHE = ServerSignalNetworkCache.get(e.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer serverPlayer)) return;

        ClientBoundSyncSignalStatesPacket syncPacket = new ClientBoundSyncSignalStatesPacket(
                ExtendedSignalsCore.serverNetworkCache().signalStates()
        );

        ExtendedSignalsNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), syncPacket);
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent e) {
        BlockEntity blockEntity = e.getLevel().getBlockEntity(e.getPos());
        if (blockEntity instanceof CoreBlockEntity blockEntitySignal) {
            blockEntitySignal.onBlockDestroyed(e.getPlayer());
        }
    }
}
