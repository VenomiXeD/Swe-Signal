package venomized.mc.mods.swsignals.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.SwSignal;

import java.util.function.Consumer;


public class CustomModelBlockItem extends BlockItem {
	private final ResourceLocation modelLocation;
	private final int lights;
	private final boolean objModel;

	public CustomModelBlockItem(Block pBlock, Properties pProperties, String modModelLoc, int lightCount, boolean objModel) {
		super(pBlock, pProperties);
		this.modelLocation = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, modModelLoc);
		this.lights = lightCount;
		this.objModel = objModel;
	}

	/**
	 * @param consumer
	 */
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			/**
			 * This method returns an ArmPose that can be defined using the {@link HumanoidModel.ArmPose#create(String, boolean, IArmPoseTransformer)} method.
			 * This allows for creating custom item use animations.
			 *
			 * @param entityLiving The entity holding the item
			 * @param hand         The hand the ArmPose will be applied to
			 * @param itemStack    The stack being held
			 * @return A custom ArmPose that can be used to define movement of the arm
			 */
			@Override
			public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
				// return HumanoidModel.ArmPose.SPYGLASS;
				return HumanoidModel.ArmPose.ITEM;
			}

			/**
			 * Queries this item's renderer.
			 * <p>
			 * Only used if {@link BakedModel#isCustomRenderer()} returns {@code true} or {@link BlockState#getRenderShape()}
			 * returns {@link RenderShape#ENTITYBLOCK_ANIMATED}.
			 * <p>
			 * By default, returns vanilla's block entity renderer.
			 */
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
					/**
					 * @param pStack
					 * @param pDisplayContext
					 * @param pPoseStack
					 * @param pBuffer
					 * @param pPackedLight
					 * @param pPackedOverlay
					 */
					@Override
					public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
						// pPoseStack.rotateAround(
						// 		new Quaternionf(
						// 				new AxisAngle4f(
						// 						System.nanoTime()/(1000f * 1000f * 1000f),0f,1f,0f
						// 				)
						// 		),.5f,0,-.5f
						// );
						BakedModel signalModel = Minecraft.getInstance().getModelManager().getModel(ResourceLocation.fromNamespaceAndPath("swsignal", "block/sw_5l_signal_post_1970"));
						if (pDisplayContext != ItemDisplayContext.GUI) {
							pPoseStack.translate(-.25f, 0, -0.25f);
						}

						pPoseStack.rotateAround(new Quaternionf(new AxisAngle4f(Mth.PI, 0f, 1f, 0f)), 0f, 0f, 0f);
						pPoseStack.scale(.5f, .5f, .5f);
						pPoseStack.translate(-.5f, -.5f, -.5f);
						pPoseStack.translate(0, 0, .5f);

						Minecraft.getInstance()
								.getBlockRenderer()
								.getModelRenderer()
								.renderModel(
										pPoseStack.last(),
										pBuffer.getBuffer(RenderType.solid()),
										null,
										signalModel,
										1, 1, 1,
										pPackedLight, pPackedOverlay
								);
					}
				};
			}
		});
	}
}