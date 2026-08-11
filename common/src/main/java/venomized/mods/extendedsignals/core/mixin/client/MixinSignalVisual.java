package venomized.mods.extendedsignals.core.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.trains.signal.SignalVisual;
import com.simibubi.create.content.trains.track.BezierTrackPointLocation;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;

@Mixin(value = SignalVisual.class, remap = false)
@OnlyIn(Dist.CLIENT)
public abstract class MixinSignalVisual {
    @Shadow
    @Final
    private TransformedInstance signalOverlay;

    @Inject(method = "setupVisual", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/signal/SignalBlockEntity;getOverlay()Lcom/simibubi/create/content/trains/signal/SignalBlockEntity$OverlayState;"), cancellable = true)
    public void extendedSignals$disableTrackSignalPlate(CallbackInfo ci) {
        if (ExtendedSignalsConfig.CLIENT.alwaysDisplaySignalPlates.getAsBoolean()) {
            signalOverlay.setVisible(true);
            signalOverlay.setChanged();
            return;
        }

        if (isRelevantItemForDisplay(Minecraft.getInstance().player.getMainHandItem()) || isRelevantItemForDisplay(Minecraft.getInstance().player.getOffhandItem())) {
            signalOverlay.setVisible(true);
            signalOverlay.setChanged();
            return;
        }
        signalOverlay.setVisible(false);
        signalOverlay.setChanged();
        ci.cancel();
    }

    @Unique
    private static boolean isRelevantItemForDisplay(ItemStack item) {
        if (item == null)
            return false;

        return AllTags.AllItemTags.TRACKS.matches(item) ||
                item.is(AllBlocks.TRACK_SIGNAL.asItem()) ||
                item.is(AllBlocks.TRACK_STATION.asItem()) ||
                item.is(AllItems.WRENCH.asItem()) ||
                BuiltInRegistries.ITEM.getKey(item.getItem()).getNamespace().contains("extended_signals");
    }
}
