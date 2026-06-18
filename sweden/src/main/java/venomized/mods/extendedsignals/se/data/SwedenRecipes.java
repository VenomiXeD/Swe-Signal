package venomized.mods.extendedsignals.se.data;

import com.mojang.datafixers.TypeRewriteRule;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;
import venomized.mods.extendedsignals.se.item.SwedenItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SwedenRecipes extends RecipeProvider {

    public SwedenRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output);
    }

    public void sequencedRecipes(Consumer<FinishedRecipe> p_recipeOutput) {
        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("endpoint_signal"))
                .require(AllBlocks.SHAFT.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.BLOCK_ENDPOINT_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("main_2_signal"))
                .require(SwedenBlocks.BLOCK_ENDPOINT_SIGNAL.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.MAIN_2_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("main_3_signal"))
                .require(SwedenBlocks.MAIN_2_SIGNAL.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.MAIN_3_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("combined_4_signal"))
                .require(SwedenBlocks.MAIN_3_SIGNAL.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.COMBINED_4_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("combined_5_signal"))
                .require(SwedenBlocks.COMBINED_4_SIGNAL.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.COMBINED_5_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("dwarf_signal"))
                .require(CommonMetal.IRON.plates)
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(CuttingRecipe::new, rb -> rb)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.nuggets))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.BLOCK_MODERN_DWARF_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("main_dwarf_signal"))
                .require(SwedenBlocks.BLOCK_MODERN_DWARF_SIGNAL.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.nuggets))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("distant_3_signal"))
                .require(CommonMetal.IRON.plates)
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllBlocks.SHAFT.asItem()))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("crossing_lights"))
                .require(CommonMetal.IRON.plates)
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(PressingRecipe::new, rb -> rb)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.CROSSING_LIGHTS.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("crossing_gate"))
                .require(Blocks.IRON_BLOCK)
                .transitionTo(SwedenItems.INCOMPLETE_CROSSING.get())
                .addStep(CuttingRecipe::new, rb -> rb)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllBlocks.MECHANICAL_BEARING))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Tags.Items.FENCES))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Tags.Items.FENCES))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Tags.Items.FENCES))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Tags.Items.FENCES))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Tags.Items.FENCES))
                .loops(1)
                .addOutput(SwedenBlocks.BLOCK_CROSSING_GATE.asItem(), 1)
                .build(p_recipeOutput);
    }

    /**
     * @param pWriter
     */
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        sequencedRecipes(pWriter);
    }
}
