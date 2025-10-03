package venomized.mc.mods.swsignals.item;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.client.sound.LoopingSound;

public class ItemTest extends Item {
    @OnlyIn(Dist.CLIENT)
    private LoopingSound sound;

    public ItemTest(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {

    }

    /**
     * @param pStack
     * @param pLevel
     * @param pEntity
     * @param pSlotId
     * @param pIsSelected
     */
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pLevel.isClientSide()) {
            return;
        }
        if (sound == null) {
            sound = new LoopingSound(AllSounds.TRAIN_X31K_1.get(), SoundSource.BLOCKS, pLevel.random);
            Minecraft.getInstance().getSoundManager()
                    .play(sound);
        }
    }
}
