package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.simibubi.create.content.trains.entity.Train;
import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;
import venomized.mods.extendedsignals.core.util.TrackedValue;

public interface ITrain {
    TrackedValue<Double> extendedSignals$trackedSpeed();

    AdvancedSignalScout advancedSignalScout();

    void requestShunting(ShuntRequest shuntRequest);

    boolean isManual();

    static ITrain cast(Train train) {
        return (ITrain) train;
    }
}
