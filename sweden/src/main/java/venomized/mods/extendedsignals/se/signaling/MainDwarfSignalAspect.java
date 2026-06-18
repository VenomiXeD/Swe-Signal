package venomized.mods.extendedsignals.se.signaling;

import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Arrays;

public record MainDwarfSignalAspect(SignalStateNode state) implements ISignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        DwarfSignalAspect.interpret(state, state.getAxisDirection())
                .applyAspect(totalTicksForBlockEntity,
                        Arrays.copyOfRange(states, 1, 5)
                );

        if (state.isStop()) {
            RGB.RED.apply(states[0]);
            RGB.BLACK.apply(states[5]);
            RGB.BLACK.apply(states[6]);
            return;
        } else {
            RGB.BLACK.apply(states[0]);
        }

        // 5, 6 bottom green light
        if (state.getMaxProceedSpeed() >= 80) {
            if (state.getNextState() == null)
                RGB.GREEN.apply(states[6]);
            else if (state.getNextState().isStop() ||
                    state.getNextState().getMaxProceedSpeed() < 80) {
                (totalTicksForBlockEntity % 20 > 10 ? RGB.GREEN : RGB.BLACK).apply(states[6]);
            } else {
                RGB.GREEN.apply(states[6]);
            }
        } else {
            if (state.getNextState() != null && state.getNextState().isStop()) {
                (totalTicksForBlockEntity % 20 > 10 ? RGB.GREEN : RGB.BLACK).apply(states[5]);
            } else {
                RGB.GREEN.apply(states[5]);
            }
        }
    }
}
