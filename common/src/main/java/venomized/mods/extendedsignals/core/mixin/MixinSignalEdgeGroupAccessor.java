package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(value = SignalEdgeGroup.class, remap = false)
public interface MixinSignalEdgeGroupAccessor {
    @Invoker("walkIntersecting")
    void extendedSignals$walkIntersecting(Consumer<SignalEdgeGroup> callback);
}
