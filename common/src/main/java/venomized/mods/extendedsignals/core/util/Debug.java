package venomized.mods.extendedsignals.core.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.createmod.catnip.data.Couple;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableInt;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundSyncSignalStatesPacket;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public class Debug {
    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent e) {
        CommandDispatcher<CommandSourceStack> dispatcher = e.getDispatcher();
        dispatcher.register(Commands.literal(ExtendedSignals.MOD_ID)
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("flush_reservations")
                        .executes(ctx -> {
                            final int groupResets = InterlockingManager.flushReservations();

                            ctx.getSource().sendSuccess(() -> Component.literal("Signal Group Ownership reset: " + groupResets), false);
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );

        dispatcher.register(Commands.literal(ExtendedSignals.MOD_ID)
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("all_force_red")
                        .executes(ctx -> {
                            if (ctx.getSource().getLevel().isClientSide())
                                return Command.SINGLE_SUCCESS;
                            ExtendedSignals.sidedNetwork(ctx.getSource().getLevel()).signalStates().forEach((k, v) -> {
                                ExtendedSignals.sidedNetwork(ctx.getSource().getLevel()).signalStates().put(k, Couple.create(
                                        SignalStateNode.STOP,
                                        SignalStateNode.STOP
                                ));
                            });

                            PacketDistributor.sendToAllPlayers(new ClientBoundSyncSignalStatesPacket(ExtendedSignals.sidedNetwork(ctx.getSource().getLevel()).signalStates()));
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }
}


