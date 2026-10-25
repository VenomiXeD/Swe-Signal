package venomized.mods.extendedsignals.core.client;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;
import venomized.mods.extendedsignals.core.item.IScrollableItem;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundRequestShuntPacket;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundScrollItemPacket;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundTranslateBlockPacket;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
    private static long CLIENT_TICK_TRACKER;

    public static long globalClientTick() {
        return CLIENT_TICK_TRACKER;
    }

    @SubscribeEvent
    public static void onScrollEvent(InputEvent.MouseScrollingEvent e) {
        if (KeyMappings.MODE_SWITCH.isDown() && Minecraft.getInstance().player != null) {
            ItemStack mainHandItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableInterface) {
                PacketDistributor.sendToServer(new ServerBoundScrollItemPacket(e.getScrollDeltaY() > 0));
                scrollableInterface.onItemScroll(Minecraft.getInstance().player, mainHandItem, e.getScrollDeltaY() > 0);

                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientPlayerPostTickEvent(PlayerTickEvent.Post e) {
        if (KeyMappings.REQUEST_SHUNT.consumeClick()) {
            if (ControlsHandler.getContraption() instanceof CarriageContraptionEntity trainCarriage) {
                float carriageYRot = trainCarriage.getYRot() - (trainCarriage.yaw + 90);
                float playerYRot = e.getEntity().getYRot();

                float rotDiff = Mth.wrapDegrees(playerYRot - carriageYRot);

                boolean front = Mth.abs(rotDiff) >= 90f;
                if (trainCarriage.checkConductors().getSecond().booleanValue())
                    front = !front;

                PacketDistributor.sendToServer(new ServerBoundRequestShuntPacket(trainCarriage.getCarriage().train.id, front, 64));
            }
        }
    }

    @SubscribeEvent
    public static void onLevelTickEvent(LevelTickEvent.Pre e) {
        CLIENT_TICK_TRACKER++;

        // if(Minecraft.getInstance().hitResult instanceof BlockHitResult bhr) {
        //     BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(bhr.getBlockPos());
        //     if (blockEntity instanceof BlockEntitySignal<?> blockEntitySignal && blockEntitySignal.getSignalSource().getPosRef() != null) {
        //         BlockPos p = blockEntitySignal.getSignalSource().getPosRef();
        //         blockEntitySignal.getLevel().addAlwaysVisibleParticle(ParticleTypes.ELECTRIC_SPARK, true, p.getX(), p.getY(), p.getZ(), 0.1D, 0.1D, 0.1D);
        //     }
        // }
    }

    @SubscribeEvent
    public static void onLevelLoadEvent(LevelEvent.Load e) {
        CLIENT_TICK_TRACKER = 0;
    }


    @SubscribeEvent
    public static void onClientInteractOnBlock(PlayerInteractEvent.RightClickBlock e) {
        if (!AllItems.WRENCH.is(e.getItemStack()))
            return;

        if (!(e.getLevel().getBlockEntity(e.getPos()) instanceof IConfigurableModelBlockEntity))
            return;

        Direction dir = e.getHitVec().getDirection().getOpposite();
        if (Screen.hasControlDown())
            dir = dir.getOpposite();

        // TODO: Networking
        PacketDistributor.sendToServer(new ServerBoundTranslateBlockPacket(e.getPos(), dir));

        e.setCanceled(true);
        e.setCancellationResult(InteractionResult.SUCCESS);
    }

    @SubscribeEvent
    public static void onClientEnterWorld(PlayerEvent.PlayerLoggedInEvent e) {
        MiscTrainDataClientSync.flushTrainData();
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent e) {
        BlockEntity blockEntity = e.getLevel().getBlockEntity(e.getPos());
        if (blockEntity instanceof CoreBlockEntity blockEntitySignal) {
            blockEntitySignal.onBlockDestroyed(e.getPlayer().level(), e.getPlayer());
        }
    }
}