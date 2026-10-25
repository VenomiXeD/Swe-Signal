package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.simibubi.create.content.trains.entity.Train;
import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;

public interface ITrain {
    AdvancedSignalScout getAdvancedSignalScout();
    void requestShunting(ShuntRequest shuntRequest);

    static ITrain cast(Train train) {
        return (ITrain) train;
    }
}
