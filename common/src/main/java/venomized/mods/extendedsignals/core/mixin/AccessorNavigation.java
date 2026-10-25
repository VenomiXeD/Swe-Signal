package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Navigation.class)
public interface AccessorNavigation {
    @Accessor
    TravellingPoint getSignalScout();
}
