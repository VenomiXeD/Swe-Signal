package venomized.mods.extendedsignals.core.signalling;

public interface ISignalStateBoundaryTransformer {
    RawSignalState transformSignalState(boolean primary, RawSignalState state);
}
