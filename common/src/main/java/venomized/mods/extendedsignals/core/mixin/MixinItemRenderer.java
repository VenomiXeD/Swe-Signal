package venomized.mods.extendedsignals.core.mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
//    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
//    public void render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci) {
    // if (pItemStack.getItem() instanceof CustomModelBlockItem) {
    // 	IClientItemExtensions.of(pItemStack.getItem()).getCustomRenderer()
    // 			.renderByItem(pItemStack, pDisplayContext, pPoseStack, pBuffer, pCombinedLight, pCombinedOverlay);
    // 	pPoseStack.popPose();
    // 	ci.cancel();
    // }

//    }
}
