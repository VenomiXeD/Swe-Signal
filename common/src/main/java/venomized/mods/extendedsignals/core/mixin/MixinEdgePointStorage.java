package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.graph.EdgePointStorage;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mods.extendedsignals.core.mixin_interfaces.IEdgePointStorage;

import java.util.List;

@Mixin(value = EdgePointStorage.class)
public abstract class MixinEdgePointStorage implements IEdgePointStorage {
    /**
     * @return
     */
    @Override
    public List<TrackEdgePoint> listEdgePoints() {
        return ((AccessorEdgePointStorage) this)
                .getPoints()
                .values()
                .stream()
                .flatMap(m -> m.values().stream())
                .toList();
    }
}
