package venomized.mods.extendedsignals.core.create.tracks;

import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public interface ISignalModifier {
    void applyModifier(RawSignalState stateToBeModified);

    boolean shouldApply();
}
