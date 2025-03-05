package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.SwBlocks;
import venomized.mc.mods.swsignals.blockentity.sw.*;
import venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals.BlockEntityDwarfSignal;
import venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals.BlockEntityEndpointSignal;
import venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals.BlockEntityRailroadCrossingDistantSignal;
import venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals.BlockEntityRailroadCrossingSignal;
import venomized.mc.mods.swsignals.blockentity.sw.mainsignals.BlockEntityFiveLightSignal;
import venomized.mc.mods.swsignals.blockentity.sw.mainsignals.BlockEntityFourLightSignal;
import venomized.mc.mods.swsignals.blockentity.sw.mainsignals.BlockEntityThreeLightSignal;
import venomized.mc.mods.swsignals.blockentity.sw.mainsignals.BlockEntityTwoLightSignal;

public final class SwBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SwSignal.MOD_ID);



	public static final RegistryObject<BlockEntityType<BlockEntityRailroadCrossingController>> BE_RAILROAD_CROSSING_CONTROLLER =
			BLOCK_ENTITIES.register("be_railroad_crossing_controller", () -> BlockEntityType.Builder.of(BlockEntityRailroadCrossingController::new,
							SwBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER.get()
					).build(null)
			);

	// #region SWEDISH CONTENT
	public static final RegistryObject<BlockEntityType<BlockEntitySignalBox>> BE_SW_SIGNAL_BOX = BLOCK_ENTITIES.register(
			BlockEntitySignalBox.NAME, () -> BlockEntityType.Builder.of(BlockEntitySignalBox::new,
					SwBlocks.BLOCK_SW_SIGNAL_BOX.get()
			).build(null)
	);

	// == SIGNALS FROM 2-5 LIGHTS ==
	public static final RegistryObject<BlockEntityType<BlockEntityTwoLightSignal>> BE_TWO_LIGHT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityTwoLightSignal.NAME, () -> BlockEntityType.Builder.of(BlockEntityTwoLightSignal::new,
							SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get()
					).build(null)
			);

	public static final RegistryObject<BlockEntityType<BlockEntityThreeLightSignal>> BE_THREE_LIGHT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityThreeLightSignal.NAME, () -> BlockEntityType.Builder.of(BlockEntityThreeLightSignal::new,
							SwBlocks.BLOCK_THREE_LIGHT_SIGNAL.get()
					).build(null)
			);

	public static final RegistryObject<BlockEntityType<BlockEntityFourLightSignal>> BE_FOUR_LIGHT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityFourLightSignal.NAME, () -> BlockEntityType.Builder.of(BlockEntityFourLightSignal::new,
							SwBlocks.BLOCK_FOUR_LIGHT_SIGNAL.get()
					).build(null)
			);

	public static final RegistryObject<BlockEntityType<BlockEntityFiveLightSignal>> BE_FIVE_LIGHT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityFiveLightSignal.NAME, () -> BlockEntityType.Builder.of(BlockEntityFiveLightSignal::new,
							SwBlocks.BLOCK_FIVE_LIGHT_SIGNAL.get()
					).build(null)
			);

	// == DISTANT SIGNALS ==

	public static final RegistryObject<BlockEntityType<BlockEntityThreeLightDistantSignal>> BE_THREE_LIGHT_DISTANT_SIGNAL =
			BLOCK_ENTITIES.register("be_sw_3l_distant_signal", () -> BlockEntityType.Builder.of(BlockEntityThreeLightDistantSignal::new,
							SwBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL.get()
					).build(null)
			);

	// == DWARF SIGNALS ==

	public static final RegistryObject<BlockEntityType<BlockEntityDwarfSignal>> BE_DWARF_SIGNAL =
			BLOCK_ENTITIES.register("be_sw_dwarf_signal_modern", () -> BlockEntityType.Builder.of(BlockEntityDwarfSignal::new,
							SwBlocks.BLOCK_MODERN_DWARF_SIGNAL.get()
					).build(null)
			);

	// == MISC SIGNALS ==

	public static final RegistryObject<BlockEntityType<BlockEntityEndpointSignal>> BE_ENDPOINT_SIGNAL =
			BLOCK_ENTITIES.register(BlockEntityEndpointSignal.NAME, () -> BlockEntityType.Builder.of(BlockEntityEndpointSignal::new,
							SwBlocks.BLOCK_ENDPOINT_SIGNAL.get()
					).build(null)
			);

	public static final RegistryObject<BlockEntityType<BlockEntityUSign>> BE_U_SIGN =
			BLOCK_ENTITIES.register("be_u_sign", () -> BlockEntityType.Builder.of(BlockEntityUSign::new,
							SwBlocks.BLOCK_U_SIGN.get()
					).build(null)
			);

	// == RAILROAD CROSSING SIGNALS ==

	public static final RegistryObject<BlockEntityType<BlockEntityRailroadCrossingSignal>> BE_RAILROAD_CROSSING_SIGNAL =
			BLOCK_ENTITIES.register("be_railroad_crossing_signal", () -> BlockEntityType.Builder.of(BlockEntityRailroadCrossingSignal::new,
							SwBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL.get()
					).build(null)
			);

	public static final RegistryObject<BlockEntityType<BlockEntityRailroadCrossingDistantSignal>> BE_RAILROAD_CROSSING_DISTANT_SIGNAL =
			BLOCK_ENTITIES.register("be_railroad_crossing_distant_signal", () -> BlockEntityType.Builder.of(BlockEntityRailroadCrossingDistantSignal::new,
							SwBlocks.BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL.get()
					).build(null)
			);
	// #endregion SWEDISH CONTENT
}