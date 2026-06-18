package venomized.mods.extendedsignals.se.data;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("test"))
                .require(AllItems.IRON_SHEET)
                .transitionTo(Blocks.STONE.asItem())
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AllItems.ELECTRON_TUBE))
                .loops(2)
                .addOutput(SwedenBlocks.MAIN_2_SIGNAL.asItem(), 1)
                .build(p_recipeOutput);
    }
}
