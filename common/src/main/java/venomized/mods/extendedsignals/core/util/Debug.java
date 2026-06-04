package venomized.mods.extendedsignals.core.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.simibubi.create.Create;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.mutable.MutableInt;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;

@Mod.EventBusSubscriber(modid = ExtendedSignalsCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Debug {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent e) {
        CommandDispatcher<CommandSourceStack> dispatcher = e.getDispatcher();

        dispatcher.register(
                Commands.literal(ExtendedSignalsCore.MOD_ID)
                        .requires(source -> source.hasPermission(4))
                        .then(
                                Commands.literal("scandistance")
                                        .then(
                                                Commands.argument("value", IntegerArgumentType.integer())
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

        dispatcher.register(Commands.literal(ExtendedSignalsCore.MOD_ID)
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("flush_reservations")
                        .executes(ctx -> {
                            MutableInt groupResetCount = new MutableInt(0);
                            MutableInt trainResetCount = new MutableInt(0);
                            Create.RAILWAYS.sided(ctx.getSource().getLevel()).signalEdgeGroups.values().forEach(g -> {
                                if (((ISignalEdgeGroup) g).extendedSignals$reservedByTrain() != null) {
                                    ((ISignalEdgeGroup) g).extendedSignals$setReservedByTrain(null);
                                    groupResetCount.increment();
                                }
                            });

                            Create.RAILWAYS.trains.values().forEach((t) -> {
                                if (((INavigation) t.navigation).extendedSignals$ownedReservedSignals().isEmpty())
                                    return;

                                trainResetCount.add(((INavigation) t.navigation).extendedSignals$ownedReservedSignals().size());
                                ((INavigation) t.navigation).extendedSignals$ownedReservedSignals().clear();
                            });

                            ctx.getSource().sendSuccess(() -> Component.literal("Signal Group Ownership reset: " + groupResetCount.intValue()), false);
                            ctx.getSource().sendSuccess(() -> Component.literal("Train Group Ownership reset: " + trainResetCount.intValue()), false);
                            return 0;
                        })
                )
        );
    }
}


