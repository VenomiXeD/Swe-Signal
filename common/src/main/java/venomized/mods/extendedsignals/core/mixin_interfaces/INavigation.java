package venomized.mods.extendedsignals.core.mixin_interfaces;

import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.create.tracks.CollectedSignal;
import venomized.mods.extendedsignals.core.create.tracks.EncounteredModifier;

import java.util.List;
import java.util.Map;

public interface INavigation {
    Map<ResourceLocation, EncounteredModifier> extendedSignals$encounteredTrackEdgePointModifiers();

    List<CollectedSignal> extendedSignals$currentScoutedEdgePoints();
}
