package venomized.mods.extendedsignals.pl.signalling;

import venomized.mods.extendedsignals.core.blockentity.LightContainer;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public record SemiAuto5Aspect(double currentSpeed, double nextSpeed) implements IMainSignalAspect {

    /**
     * @param seconds
     * @param states
     */
    @Override
    public void applyAspect(float seconds, LightContainer states) {
        if (currentSpeed == -1) {
            states.powered("l2");
            return;
        }

        handleDistantAspect(seconds, states);
        handleMainAspect(seconds, states);
    }

    private void handleDistantAspect(float seconds, LightContainer states) {
        if (nextSpeed <= -1) {
            states.powered("l1");
            return;
        }
        if (nextSpeed <= 60) {
            if (ISignalAspect.blink(seconds))
                states.powered("l1");
            return;
        }

        if (nextSpeed <= 100) {
            if (ISignalAspect.blink(seconds))
                states.powered("l0");
            return;
        }

        states.powered("l0");
    }

    private void handleMainAspect(float seconds, LightContainer states) {
        if (currentSpeed <= 100)
            states.powered("l3");

        // Missing the weird multicolor light bar
    }

    public static SemiAuto5Aspect interpret(SignalStateNode signalState) {
        return new SemiAuto5Aspect(
                signalState.isStop() ? -1 : signalState.getMaxProceedSpeed(),
                signalState.getNextState() == null || signalState.getNextState().isStop() ? -1 : signalState.getNextState().getMaxProceedSpeed()
        );
    }
}
