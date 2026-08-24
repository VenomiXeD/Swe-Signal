package venomized.mods.extendedsignals.core.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.trainmap.TrainMapManager;
import com.simibubi.create.compat.trainmap.TrainMapRenderer;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.*;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.station.GlobalStation;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.ArrayList;
import java.util.List;

@Mixin(TrainMapManager.class)
public abstract class MixinTrainMapManager {
    @Inject(method = "drawPoints", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/graph/TrackGraph;getPoints(Lcom/simibubi/create/content/trains/graph/EdgePointType;)Ljava/util/Collection;"))
    private static void extendedSignals$draw$drawSignals(GuiGraphics graphics, int mouseX, int mouseY, Object hoveredElement, Rect2i bounds, CallbackInfoReturnable<Object> cir, @Local(name = "graph") TrackGraph graph, @Local(name = "pose") PoseStack pose, @Local(name = "hoveredElement", index = 0, ordinal = 0, argsOnly = true) LocalRef<Object> hElement) {
        for (SignalBoundary signal : graph.getPoints(EdgePointType.SIGNAL)) {
            Couple<TrackNodeLocation> edgeLocation = signal.edgeLocation;
            TrackNode node = graph.locateNode(edgeLocation.getFirst());
            TrackNode other = graph.locateNode(edgeLocation.getSecond());
            if (node == null || other == null)
                continue;
            if (node.getLocation().dimension != TrainMapRenderer.INSTANCE.trackingDim)
                continue;

            TrackEdge edge = graph.getConnection(Couple.create(node, other));
            if (edge == null)
                continue;

            double tLength = signal.getLocationOn(edge);
            double t = tLength / edge.getLength();
            Vec3 position = edge.getPosition(graph, t);

            int x = Mth.floor(position.x());
            int y = Mth.floor(position.z());

            if (!bounds.contains(x, y))
                continue;

            Vec3 diff = edge.getDirectionAt(tLength)
                    .normalize();
            int rotation = Mth.positiveModulo(Mth.floor(0.5
                            + (Math.atan2(diff.z, diff.x) * Mth.RAD_TO_DEG + 90 + (signal.isPrimary(node) ? 180 : 0)) / 45),
                    8);

            AllGuiTextures sprite = AllGuiTextures.TRAINMAP_STATION_ORTHO;
            AllGuiTextures highlightSprite = AllGuiTextures.TRAINMAP_STATION_ORTHO_HIGHLIGHT;
            if (rotation % 2 != 0) {
                sprite = AllGuiTextures.TRAINMAP_STATION_DIAGO;
                highlightSprite = AllGuiTextures.TRAINMAP_STATION_DIAGO_HIGHLIGHT;
            }

            boolean highlight = hoveredElement == null && Math.max(Math.abs(mouseX - x), Math.abs(mouseY - y)) < 3;

            pose.pushPose();
            pose.translate(x - 2, y - 2, 5);

            pose.translate(sprite.getWidth() / 2.0, sprite.getHeight() / 2.0, 0);
            pose.mulPose(Axis.ZP.rotationDegrees(90 * (rotation / 2)));
            pose.translate(-sprite.getWidth() / 2.0, -sprite.getHeight() / 2.0, 0);

            sprite.render(graphics, 0, 0);
            sprite.render(graphics, 0, 0);

            if (highlight) {
                pose.translate(0, 0, 5);
                highlightSprite.render(graphics, -1, -1);
                hElement.set(signal);
            }

            pose.popPose();
        }
    }

    @Inject(method = "renderAndPick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch()V", shift = At.Shift.AFTER), cancellable = true)
    private static void extendedSignals$additionalRenderAndPick(GuiGraphics graphics, int mouseX, int mouseY, boolean linearFiltering, Rect2i bounds, CallbackInfoReturnable<List<FormattedText>> cir, @Local(name = "hoveredElement") Object hoveredElement) {
        if (hoveredElement instanceof SignalBoundary signal) {
            cir.setReturnValue(extendedSignals$listSignalDetails(signal));
        }
    }

    @Unique
    private static List<FormattedText> extendedSignals$listSignalDetails(SignalBoundary signal) {
        ReferenceArrayList<FormattedText> text = new ReferenceArrayList<>();
        text.add(Component.translatable("train_map.extended_signals.signal.id", signal.getId().toString()));
        for (boolean side : Iterate.falseAndTrue) {
            text.add(Component.literal("-".repeat(10)));
            SignalStateNode state = ExtendedSignals.clientNetworkCache().getSignalState(signal.id, side);
            extendedSignals$populateSignalDetails(text, state, side, 0);
            if (Screen.hasShiftDown()) {
                text.add(Component.translatable("train_map.extended_signals.signal.distance_next",
                        state.getDistanceToNextSignal() == -1 ? "N/A" : "%.2f".formatted(state.getDistanceToNextSignal())));

                if (state.getNextState() != null)
                    extendedSignals$populateSignalDetails(text, state.getNextState(), side, 1);
            }
        }

        return text;
    }

    @Unique
    private static void extendedSignals$populateSignalDetails(final List<FormattedText> text, SignalStateNode state, boolean side, int depth) {
        text.add(Component.literal("    ".repeat(depth)).append(Component.translatable("train_map.extended_signals.signal.direction", side ? "Positive " : "Negative")));
        text.add(Component.literal("    ".repeat(depth)).append(Component.translatable("train_map.extended_signals.signal.state", state.isStop() ? "Stop" : "Proceed")));
        if (state.isProceed()) {
            text.add(Component.literal("    ".repeat(depth))
                    .append(Component.translatable(
                            "train_map.extended_signals.signal.max_speed",
                            state.getMaxProceedSpeed() == Double.MAX_VALUE ? "Any" : "%.2f".formatted(state.getMaxProceedSpeed())
                    ))
            );
        }
    }
}
