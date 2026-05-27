package venomized.mods.extendedsignals.core.signalling;

public interface ISignalStateBoundaryTransformer {
    SignalStateNode transformSignalState(boolean primary, SignalStateNode state);
}
