package venomized.mods.extendedsignals.core.mixin_interfaces;

import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.create.tracks.CollectedSignal;
import venomized.mods.extendedsignals.core.create.tracks.EncounteredPoint;

import java.util.List;
import java.util.Map;

public interface INavigation {
    Map<ResourceLocation, EncounteredPoint> extendedSignals$encounteredTrackEdgePointModifiers();

    List<CollectedSignal> extendedSignals$currentScoutedEdgePoints();
}
