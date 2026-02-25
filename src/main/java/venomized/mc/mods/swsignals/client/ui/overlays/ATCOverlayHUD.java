package venomized.mc.mods.swsignals.client.ui.overlays;

import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.core.SwSignal;

@OnlyIn(Dist.CLIENT)
public class ATCOverlayHUD implements LayeredDraw.Layer {
    public static final ResourceLocation ATC_OVERLAY = SwSignal.resource("atc_overlay");
    // public static IGuiOverlay OVERLAY_RENDER = ATCOverlay::render;
    public static int overspeedBlinkTick = 0;

    public static double currentATCLimit = 1d;

    private static Carriage getCarriage() {
        if (!(ControlsHandler.getContraption() instanceof CarriageContraptionEntity cce))
            return null;
        return cce.getCarriage();
    }

    public static void setATCLimit(double limit) {
        currentATCLimit = Math.max(0, limit);
    }

    public static void tick() {
        Carriage c = getCarriage();
        if (c != null) {
            double speedPercent =
                    Math.abs(c.train.speed) / (c.train.maxSpeed() * AllConfigs.server().trains.manualTrainSpeedModifier.getF());
            if (speedPercent > currentATCLimit) {
                overspeedBlinkTick = (overspeedBlinkTick + 1) % 20;
            } else {
                overspeedBlinkTick = 0;
            }
        } else {
            overspeedBlinkTick = 0;
        }
    }

    //                 overlay.render(this, guiGraphics, partialTick, screenWidth, screenHeight);
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker tracker) {
        Minecraft mc = Minecraft.getInstance();
        Carriage carriage = getCarriage();
        if (carriage == null) {
            return;
        }

        guiGraphics.pose().pushPose();
        // guiGraphics.pose().rotateAround(
        // 		new Quaternionf(
        // 				new AxisAngle4f(
        // 						Mth.DEG_TO_RAD*System.nanoTime()/10000000f*Mth.PI,0,0,1)),
        // 		guiGraphics.guiWidth() / 2,
        // 		guiGraphics.guiHeight() - 50,
        // 		0
        // );

        int w = guiGraphics.guiWidth();
        int h = guiGraphics.guiHeight();

        guiGraphics.drawString(
                mc.font,
                "Current ATC Limit: %.2f".formatted(currentATCLimit * 100d) + "%",
                w / 2 - 50,
                h - 55,
                overspeedBlinkTick > 10 ? 0xFF0000 : 0xFFFFFF
        );
        guiGraphics.pose().popPose();
    }
}
