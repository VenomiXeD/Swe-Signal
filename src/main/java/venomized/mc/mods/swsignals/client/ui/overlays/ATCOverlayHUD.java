package venomized.mc.mods.swsignals.client.ui.overlays;

import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;

@OnlyIn(Dist.CLIENT)
public class ATCOverlayHUD {
	// public static IGuiOverlay OVERLAY_RENDER = ATCOverlay::render;
	public static int overspeedBlinkTick = 0;

	public static double currentATCLimit = 1d;

	private static Carriage getCarriage() {
		if (!(ControlsHandler.getContraption() instanceof CarriageContraptionEntity cce))
			return null;
		return cce.getCarriage();
	}
//                 overlay.render(this, guiGraphics, partialTick, screenWidth, screenHeight);
	public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int w, int h) {

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

		guiGraphics.drawString(
				forgeGui.getFont(),
				"Current ATC Limit: %.2f".formatted(currentATCLimit*100d) + "%",
				w / 2 - 50,
				h - 55,
				overspeedBlinkTick > 10 ? 0xFF0000 : 0xFFFFFF
		);
		guiGraphics.pose().popPose();
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
}
