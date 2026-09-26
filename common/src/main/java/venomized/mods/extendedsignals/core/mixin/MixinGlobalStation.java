package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.station.GlobalStation;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;

@Mixin(value = GlobalStation.class, remap = false)
public abstract class MixinGlobalStation implements IExtendedEdgePoint<GlobalStation> {
    /**
     * @return
     */
    @Override
    public Component getName() {
        return Component.translatable("train_map.extended_signals.edgepoint.train_station.name");
    }
}
