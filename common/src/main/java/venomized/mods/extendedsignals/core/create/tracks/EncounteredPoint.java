package venomized.mods.extendedsignals.core.create.tracks;

import venomized.mods.extendedsignals.core.create.tracks.points.ISignalStateModifier;

import java.lang.ref.WeakReference;

public record EncounteredPoint(boolean front, WeakReference<ISignalStateModifier> modifier) {
}
