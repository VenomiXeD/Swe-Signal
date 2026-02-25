package venomized.mc.mods.swsignals.blockentity;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.Pair;
import net.createmod.catnip.lang.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import org.checkerframework.checker.units.qual.C;

import java.util.Optional;
import java.util.function.IntFunction;

public interface ISignalTunerBindable {
    default boolean isSource() {
        return true;
    }

    default boolean isDestination() {
        return true;
    }

    /**
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity source block destination
     * @param mode
     * @return
     */
    default Pair<InteractionResult, MutableComponent> onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
        return Pair.of(InteractionResult.FAIL, Component.literal("Invalid Data Source Tile"));
    }

    /**
     * Signal Box A -> Create Signal; Signal Box A is the target
     *
     * @param targetBlockEntity target block destination
     * @param mode
     */
    default Pair<InteractionResult, ? extends Component> onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode) {
        return Pair.of(InteractionResult.CONSUME, Component.empty());
    }

    enum SignalTunerMode implements StringRepresentable {
        DISCONNECT_ALL,
        DISCONNECT,
        CONNECT,
        CONFIGURE;

        private static final IntFunction<SignalTunerMode> BY_ID = ByIdMap.continuous(SignalTunerMode::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StringRepresentable.StringRepresentableCodec<SignalTunerMode> CODEC = StringRepresentable.fromEnum(SignalTunerMode::values);
        public static final StreamCodec<ByteBuf, SignalTunerMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SignalTunerMode::ordinal);

        public SignalTunerMode next(boolean up) {
            return BY_ID.apply(ordinal() + (up ? 1 : -1));
        }

        @Override
        public String getSerializedName() {
            return Lang.asId(this.name());
        }
    }
}
