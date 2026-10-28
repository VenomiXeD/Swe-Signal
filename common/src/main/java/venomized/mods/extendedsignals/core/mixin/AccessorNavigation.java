package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Navigation.class)
public interface AccessorNavigation {
    @Accessor
    TravellingPoint getSignalScout();

    @Invoker(value = "currentSignalResolved")
    boolean signalResolved();
}
