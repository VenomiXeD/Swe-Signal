package venomized.mc.mods.swsignals.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;

public class SwBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

    public static final RegistryObject<BlockTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = BLOCKS.register(BlockTwoLightSignal.BLOCK_NAME, BlockTwoLightSignal::new);
    public static final RegistryObject<BlockThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = BLOCKS.register(BlockThreeLightSignal.BLOCK_NAME, BlockThreeLightSignal::new);
}
