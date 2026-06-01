package venomized.mods.extendedsignals.core.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.Global;

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

                                                            Global.SCAN_DISTANCE = value;

                                                            ctx.getSource().sendSuccess(
                                                                    () -> Component.literal("Scan distance set to " + value),
                                                                    false
                                                            );

                                                            return value;
                                                        })
                                        )
                        )
        );
    }
}


