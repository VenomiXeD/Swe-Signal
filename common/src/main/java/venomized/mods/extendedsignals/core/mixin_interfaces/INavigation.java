package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.simibubi.create.content.trains.entity.TravellingPoint;
import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.EncounteredPoint;

import java.util.List;
import java.util.Map;

public interface INavigation {
    /**
     * Signal scout Travelling Point
     *
     * @return
     */
    TravellingPoint signalScoutTP();
}
