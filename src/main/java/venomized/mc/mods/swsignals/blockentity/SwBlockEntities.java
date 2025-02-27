package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.SwBlocks;

public final class SwBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SwSignal.MOD_ID);
	public static final RegistryObject<BlockEntityType<BlockEntityTwoLightSignalBlock>> BE_TWO_LIGHT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityTwoLightSignalBlock.NAME, () -> BlockEntityType.Builder.of(BlockEntityTwoLightSignalBlock::new,

							SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get()

					).build(null)
			);

	public static final RegistryObject<BlockEntityType<BlockEntityThreeLightSignalBlock>> BE_THREE_LIGHT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityThreeLightSignalBlock.NAME, () -> BlockEntityType.Builder.of(BlockEntityThreeLightSignalBlock::new,

							SwBlocks.BLOCK_THREE_LIGHT_SIGNAL.get()

					).build(null)
			);

}
