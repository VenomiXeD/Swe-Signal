package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.ExtendedSignals;

@OnlyIn(Dist.CLIENT)
public class SignalRendererHelper {
    public static final int FULLBRIGHT = 0xFFFFFF;

    public static ResourceLocation SIGNAL_LIGHT_TEX_LOC =
            ResourceLocation.fromNamespaceAndPath(
                    ExtendedSignals.MOD_ID,
                    "textures/block/light_1.png"
            );
}
