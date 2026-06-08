package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.api.schematic.requirement.SpecialBlockItemRequirement;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.IHaveBigOutline;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

@Mixin(TrackBlock.class)
public abstract class MixinCreateTrainTrack extends Block
        implements IBE<TrackBlockEntity>, IWrenchable, ITrackBlock, SpecialBlockItemRequirement, ProperWaterloggedBlock, IHaveBigOutline {

    public MixinCreateTrainTrack(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "use", at = @At("HEAD"))
    public void onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        Item item = player.getItemInHand(hand).getItem();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TrackBlockEntity tbe) {// && item instanceof TrackDecorationItem tdi) {
            ExtendedSignalsCore.LOGGER.info("adding decoration item");
        }
    }
}
