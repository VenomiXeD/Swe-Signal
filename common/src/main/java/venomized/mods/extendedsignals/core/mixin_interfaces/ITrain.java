package venomized.mods.extendedsignals.core.mixin_interfaces;

import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;

public interface ITrain {
    AdvancedSignalScout getAdvancedSignalScout();
    void requestShunting(ShuntRequest shuntRequest);
}
