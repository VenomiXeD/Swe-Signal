package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;

@Mixin(value = GlobalRailwayManager.class, remap = false)
public abstract class MixinGlobalRailwayManager {
    @ModifyReturnValue(method = "isTrackGraphDebugActive", at = @At("RETURN"))
    private static boolean isTrackGraphDebugActive(boolean value) {
        return value || extendedSignals$shouldDisplayGraph();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void extendedSignals$tick(Level level, CallbackInfo ci) {
        if (level.isClientSide()) {
            return;
        }
        ExtendedSignals.serverNetworkCache().tickSynchronize();
    }

    @Unique
    @OnlyIn(Dist.CLIENT)
    private static boolean extendedSignals$shouldDisplayGraph() {
        if (Minecraft.getInstance().player == null)
            return false;


        boolean anyHeldTracks = AllTags.AllItemTags.TRACKS.matches(Minecraft.getInstance().player.getMainHandItem()) || AllTags.AllItemTags.TRACKS.matches(Minecraft.getInstance().player.getMainHandItem());
        return anyHeldTracks && ExtendedSignalsConfig.CLIENT.overlay_showTrackGraph.getAsBoolean();
    }
}