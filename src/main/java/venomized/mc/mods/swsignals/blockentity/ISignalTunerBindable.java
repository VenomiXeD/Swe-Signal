package venomized.mc.mods.swsignals.blockentity;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.util.Optional;

public interface ISignalTunerBindable extends IForgeBlockEntity {
    default void onStartBinding() {
    }

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
    default Pair<InteractionResult, Component> onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
        return Pair.of(InteractionResult.FAIL, Component.literal("Invalid Data Source Tile"));
    }

    /**
     * Signal Box A -> Create Signal; Signal Box A is the target
     *
     * @param targetBlockEntity target block destination
     * @param mode
     */
    default Pair<InteractionResult, Component> onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode) {
        return Pair.of(InteractionResult.CONSUME, Component.empty());
    }

    enum SignalTunerMode {
        DISCONNECT_ALL,
        DISCONNECT,
        CONNECT,
        CONFIGURE
    }
}
