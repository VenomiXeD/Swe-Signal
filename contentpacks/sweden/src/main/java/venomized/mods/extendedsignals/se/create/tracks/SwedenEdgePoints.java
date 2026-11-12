package venomized.mods.extendedsignals.se.create.tracks;

import com.simibubi.create.content.trains.graph.EdgePointType;
import venomized.mods.extendedsignals.core.ExtendedSignals;

public class SwedenEdgePoints {
    public static final EdgePointType<ATCBalise> BALISE = EdgePointType.register(
            ExtendedSignals.res("balise"), ATCBalise::new
    );
}
