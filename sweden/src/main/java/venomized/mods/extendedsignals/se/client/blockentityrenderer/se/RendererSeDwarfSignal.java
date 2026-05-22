package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.client.blockentityrenderer.SignalRendererHelper;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityDwarfSignal;

public abstract class RendererSeDwarfSignal<T extends BlockEntityDwarfSignal> extends BlockEntityRendererBase<T> {
    public RendererSeDwarfSignal(BlockEntityRendererProvider.Context context) {
    }
}
