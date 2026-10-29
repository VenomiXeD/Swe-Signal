package venomized.mods.extendedsignals.core.create;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.TranslatableEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum SignalPolicy implements TranslatableEnum {
    JOURNEY,
    SHUNT_MOVEMENT,
    SHUNT_COUPLING;

    private MutableComponent translated;

    public static StreamCodec<FriendlyByteBuf, SignalPolicy> STREAM_CODEC = StreamCodec.of(FriendlyByteBuf::writeEnum, buffer -> buffer.readEnum(SignalPolicy.class));

    /**
     * @return
     */
    @Override
    public @NotNull Component getTranslatedName() {
        return Component.translatable("extended_signals.schedule.instruction.signal_mode.mode." + this.name().toLowerCase());
    }

    public static List<Component> translatedOptions() {
        return Arrays.stream(SignalPolicy.values()).map(SignalPolicy::getTranslatedName).toList();
    }
}
