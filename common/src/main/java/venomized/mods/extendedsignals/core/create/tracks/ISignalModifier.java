package venomized.mods.extendedsignals.core.create.tracks;

import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface ISignalModifier {
    void applyModifier(SignalStateNode stateToBeModified);

    boolean shouldApply();
}
