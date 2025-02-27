package venomized.mc.mods.swsignals.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;

public class SwBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

    public static final RegistryObject<BlockModernTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = BLOCKS.register(BlockModernTwoLightSignal.BLOCK_NAME, BlockModernTwoLightSignal::new);
    public static final RegistryObject<BlockModernThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = BLOCKS.register(BlockModernThreeLightSignal.BLOCK_NAME, BlockModernThreeLightSignal::new);
    public static final RegistryObject<BlockModernFourLightSignal> BLOCK_FOUR_LIGHT_SIGNAL = BLOCKS.register(BlockModernFourLightSignal.BLOCK_NAME, BlockModernFourLightSignal::new);
    public static final RegistryObject<BlockModernFiveLightSignal> BLOCK_FIVE_LIGHT_SIGNAL = BLOCKS.register(BlockModernFiveLightSignal.BLOCK_NAME, BlockModernFiveLightSignal::new);

    public static final RegistryObject<BlockUSign> BLOCK_U_SIGN = BLOCKS.register(BlockUSign.BLOCK_NAME,BlockUSign::new);
}
