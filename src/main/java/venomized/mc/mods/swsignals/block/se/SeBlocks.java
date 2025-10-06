package venomized.mc.mods.swsignals.block.se;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.simibubi.create.infrastructure.data.CreateRegistrateTags;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.AllBlocks;
import venomized.mc.mods.swsignals.block.BlockATCController;
import venomized.mc.mods.swsignals.block.BlockRailroadCrossingController;
import venomized.mc.mods.swsignals.block.se.crossing.BlockCrossingGate;
import venomized.mc.mods.swsignals.block.se.crossing.BlockThreeLightCrossingSignal;
import venomized.mc.mods.swsignals.create.tracks.ATCController;

/**
 * Swedish railway content (blocks)
 */
public class SeBlocks {
    // == SWEDISH CONTENT ==
    public static final BlockEntry<BlockSignalBox> BLOCK_SIGNAL_BOX = AllBlocks.modelledBlock("signals.se.signal_box", BlockSignalBox::new)
            .register();
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntry<BlockModernTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = AllBlocks.modelledBlock("signals.se.2l_signal_post_1970", BlockModernTwoLightSignal::new)
            .register();
    public static final BlockEntry<BlockModernThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = AllBlocks.modelledBlock("signals.se.3l_signal_post_1970", BlockModernThreeLightSignal::new)
            .register();
    public static final BlockEntry<BlockModernFourLightSignal> BLOCK_FOUR_LIGHT_SIGNAL = AllBlocks.modelledBlock("signals.se.4l_signal_post_1970", BlockModernFourLightSignal::new)
            .register();
    public static final BlockEntry<BlockModernFiveLightSignal> BLOCK_FIVE_LIGHT_SIGNAL = AllBlocks.modelledBlock("signals.se.5l_signal_post_1970", BlockModernFiveLightSignal::new)
            .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntry<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = AllBlocks.modelledBlock("signals.se.3l_distant_signal_post_1970", BlockModernThreeLightDistantSignal::new)
            .register();
    // == DWARF SIGNALS ==
    public static final BlockEntry<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = AllBlocks.modelledBlock("signals.se.4l_dwarf_signal_post_1970", BlockModernDwarfSignal::new)
            .register();
    public static final BlockEntry<BlockModernMainDwarfSignal> BLOCK_MODERN_MAIN_DWARF_SIGNAL = AllBlocks.modelledBlock("signals.se.7l_dwarf_main_signal_post_1970", BlockModernMainDwarfSignal::new)
            .register();
    // == MISC SIGNALS ==
    public static final BlockEntry<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = AllBlocks.modelledBlock("signals.se.1l_endpoint_post_1920", BlockModernEndpointSignal::new)
            .register();
    public static final BlockEntry<BlockGeneric45DegreeBlock> BLOCK_U_SIGN = AllBlocks.modelledBlock("signals.se.u_sign", BlockGeneric45DegreeBlock::new)
            .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntry<BlockRailroadCrossingSignal> BLOCK_RAILROAD_CROSSING_SIGNAL = AllBlocks.modelledBlock("signals.se.1l_railroad_crossing_signal_2_post_1970", BlockRailroadCrossingSignal::new)
            .register();
    public static final BlockEntry<BlockRailroadCrossingDistantSignal> BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL = AllBlocks.modelledBlock("signals.se.3l_distant_railroad_crossing_signal_post_1970", BlockRailroadCrossingDistantSignal::new)
            .register();
    public static final BlockEntry<BlockCrossingGate> BLOCK_CROSSING_GATE = AllBlocks.modelledBlock("signals.se.crossing.base", BlockCrossingGate::new)
            .register();
    public static final BlockEntry<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = AllBlocks.modelledBlock("signals.se.crossing.controller", BlockRailroadCrossingController::new)
            .register();
    public static final BlockEntry<BlockATCController> BLOCK_ATC_CONTROLLER = SwSignal.REGISTRATE.get().block("atc_controller", BlockATCController::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            .item((block, prop) -> new TrackTargetingBlockItem(block, prop, ATCController.ATC))
            .build()
            .register();
    public static final BlockEntry<BlockThreeLightCrossingSignal> BLOCK_THREE_LIGHT_CROSSING_SIGNAL = AllBlocks.signalBlock("se/crossing", "3l_crossing_lights_post_1970", BlockThreeLightCrossingSignal::new)
            .register();

    public static void init() {
    }
}
