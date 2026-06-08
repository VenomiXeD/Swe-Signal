package venomized.mods.extendedsignals.core.mixin_interfaces;

import venomized.mods.extendedsignals.core.signalling.ShuntRequest;

public interface ITrain {
    void requestShunting(ShuntRequest shuntRequest);
}
