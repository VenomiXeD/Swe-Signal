package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.graph.EdgePointStorage;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.UUID;

@Mixin(value = EdgePointStorage.class, remap = false)
public interface MixinEdgePointStorageAccessor {
    @Accessor("pointsByType")
    Map<EdgePointType<?>, Map<UUID, TrackEdgePoint>> getPoints();
}
