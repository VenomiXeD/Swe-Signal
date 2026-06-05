package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.electronwill.nightconfig.core.conversion.ConversionTable;
import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.create.tracks.ISignalModifier;

import java.util.Map;

public interface INavigation {
    Map<ResourceLocation, ISignalModifier> extendedSignals$activeModifiers();
}
