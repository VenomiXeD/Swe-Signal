package venomized.mods.extendedsignals.core.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.content.trains.signal.SignalVisual;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;

@Mixin(value = GlobalRailwayManager.class, remap = false)
@OnlyIn(Dist.CLIENT)
public abstract class MixinGlobalRailwayManager {
    @ModifyReturnValue(method = "isTrackGraphDebugActive", at = @At("RETURN"))
    private static boolean isTrackGraphDebugActive(boolean value) {
        return value || extendedSignals$shouldDisplayGraph();

    }

    @Unique
    private static boolean extendedSignals$shouldDisplayGraph() {
        if (Minecraft.getInstance().player == null)
            return false;


        boolean anyHeldTracks = AllTags.AllItemTags.TRACKS.matches(Minecraft.getInstance().player.getMainHandItem()) || AllTags.AllItemTags.TRACKS.matches(Minecraft.getInstance().player.getMainHandItem());
        return anyHeldTracks && ExtendedSignalsConfig.CLIENT.showTrackGraphsWhenUsingTracks.getAsBoolean();
    }
}