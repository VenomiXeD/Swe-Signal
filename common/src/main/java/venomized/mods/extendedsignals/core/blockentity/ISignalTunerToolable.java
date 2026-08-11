package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public interface ISignalTunerToolable {
    default boolean isSource() {
        return true;
    }

    default boolean isReader() {
        return true;
    }

    /**
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity source block destination
     * @param mode
     * @param useContext
     * @return
     */
    default InteractionResult sourceBindingToReader(ISignalTunerToolable sourceBlockEntity, SignalTunerMode mode, UseOnContext useContext) {
        return InteractionResult.PASS;
    }

    /**
     * Signal Box A -> Create Signal; Signal Box A is the target
     *
     * @param targetBlockEntity target block destination
     * @param mode
     * @param useContext
     */
    default InteractionResult readerBindingToSource(ISignalTunerToolable targetBlockEntity, SignalTunerMode mode, UseOnContext useContext) {
        return InteractionResult.PASS;
    }

    default InteractionResult onSignalToolInteract(SignalTunerMode mode, UseOnContext context) {
        return InteractionResult.PASS;
    }

    enum SignalTunerMode {
        CONNECT,
        DISCONNECT,
        CONFIGURE,
        INFO
    }
}
