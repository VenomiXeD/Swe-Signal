package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.graph.EdgePointStorage;
import com.simibubi.create.content.trains.graph.TrackGraph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TrackGraph.class, remap = false)
public interface TrackGraphAccessor {
    @Accessor("edgePoints")
    EdgePointStorage getEdgePoints();
}
