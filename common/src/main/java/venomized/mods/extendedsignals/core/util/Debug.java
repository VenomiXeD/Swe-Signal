package venomized.mods.extendedsignals.core.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.apache.commons.lang3.mutable.MutableInt;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;

public class Debug {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent e) {
        CommandDispatcher<CommandSourceStack> dispatcher = e.getDispatcher();

        dispatcher.register(Commands.literal(ExtendedSignals.MOD_ID)
                .requires(source -> source.hasPermission(4))
                .then(
                        Commands.literal("scandistance")
                                .then(Commands.argument("value", IntegerArgumentType.integer())
                                        .executes(ctx -> {
                                            int value = IntegerArgumentType.getInteger(ctx, "value");

                                            // Global.SCAN_DISTANCE = value;
                                            ctx.getSource().sendSuccess(
                                                    () -> Component.literal("Scan distance command replaced with a server config value"),
                                                    false
                                            );

                                            return value;
                                        })
                                )
                )
        );

        dispatcher.register(Commands.literal(ExtendedSignals.MOD_ID)
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("flush_reservations")
                        .executes(ctx -> {
                            MutableInt groupResetCount = new MutableInt(0);
                            MutableInt trainResetCount = new MutableInt(0);
                            InterlockingManager.flushReservations();

                            ctx.getSource().sendSuccess(() -> Component.literal("Signal Group Ownership reset: " + groupResetCount.intValue()), false);
                            ctx.getSource().sendSuccess(() -> Component.literal("Train Group Ownership reset: " + trainResetCount.intValue()), false);
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }
}


