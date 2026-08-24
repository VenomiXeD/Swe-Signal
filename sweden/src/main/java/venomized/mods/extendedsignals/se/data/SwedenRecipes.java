package venomized.mods.extendedsignals.se.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;
import venomized.mods.extendedsignals.se.item.SwedenItems;

import java.util.concurrent.CompletableFuture;

public class SwedenRecipes extends RecipeProvider {

    public SwedenRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    /**
     * @param recipeOutput
     */
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        sequencedRecipes(recipeOutput);
    }

    public void sequencedRecipes(RecipeOutput p_recipeOutput) {
        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("endpoint_signal"))
                .require(AllBlocks.SHAFT.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.SIGNAL_ENDPOINT.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("main_2_signal"))
                .require(SwedenBlocks.SIGNAL_ENDPOINT.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.SIGNAL_MAIN_2_MODERN.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("main_3_signal"))
                .require(SwedenBlocks.SIGNAL_MAIN_2_MODERN.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.SIGNAL_MAIN_3_MODERN.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("combined_4_signal"))
                .require(SwedenBlocks.SIGNAL_MAIN_3_MODERN.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.SIGNAL_COMBINED_4_MODERN.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("combined_5_signal"))
                .require(SwedenBlocks.SIGNAL_COMBINED_4_MODERN.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.SIGNAL_COMBINED_5_MODERN.asItem(), 1)
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
                .addOutput(SwedenBlocks.SIGNAL_DWARF_MODERN.asItem(), 1)
                .build(p_recipeOutput);

        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("main_dwarf_signal"))
                .require(SwedenBlocks.SIGNAL_DWARF_MODERN.asItem())
                .transitionTo(SwedenItems.INCOMPLETE_SIGNAL.get())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.plates))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CommonMetal.IRON.nuggets))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(1)
                .addOutput(SwedenBlocks.SIGNAL_MAIN_DWARF_MODERN.asItem(), 1)
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
                .addOutput(SwedenBlocks.SIGNAL_DISTANT_3_MODERN.asItem(), 1)
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
                .addOutput(SwedenBlocks.CROSSING_LIGHTS_MODERN.asItem(), 1)
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
                .addOutput(SwedenBlocks.CROSSING_GATE_MODERN.asItem(), 1)
                .build(p_recipeOutput);
    }
}
