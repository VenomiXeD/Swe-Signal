package venomized.mods.extendedsignals.core.mixin_interfaces;

import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.create.tracks.ISignalModifier;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface INavigation {
    Map<ResourceLocation, ISignalModifier> extendedSignals$activeModifiers();

    Set<UUID> extendedSignals$ownedReservedSignals();
}
