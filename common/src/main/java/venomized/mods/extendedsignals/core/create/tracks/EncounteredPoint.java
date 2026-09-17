package venomized.mods.extendedsignals.core.create.tracks;

import venomized.mods.extendedsignals.core.create.tracks.points.ISignalStateModifier;

public record EncounteredPoint(boolean front, ISignalStateModifier modifier) {
}
