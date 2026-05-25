package venomized.mods.extendedsignals.core.mixin_interfaces;

import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.create.tracks.ISignalModifier;

import java.util.Map;

public interface INavigationAccessor {
    Map<ResourceLocation, ISignalModifier> extendedSignals$activeModifiers();
}
