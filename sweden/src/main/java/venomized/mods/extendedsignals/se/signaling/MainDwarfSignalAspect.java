package venomized.mods.extendedsignals.se.signaling;

import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public record MainDwarfSignalAspect(SignalStateNode state) implements ISignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalContainer states) {
        DwarfSignalAspect.interpret(state, state.getAxisDirection()).applyAspect(seconds, states);

        if (state.isStop()) {
            states.powered("stop");
            return;
        }

        boolean blink = ISignalAspect.blink(seconds, 80, 375f / 1000f);
        // 5, 6 bottom green light
        if (state.getMaxProceedSpeed() >= 80) {
            if (state.getNextState() == null)
                states.powered("green_right");
            else if (state.getNextState().isStop() || state.getNextState().getMaxProceedSpeed() < 80) {
                if (blink)
                    states.powered("green_left");
            } else {
                states.powered("green_right");
            }
        } else {
            if (state.getNextState() != null && state.getNextState().isStop()) {
                if (blink)
                    states.powered("green_left");
            } else {
                states.powered("green_left");
            }
        }
    }
}
