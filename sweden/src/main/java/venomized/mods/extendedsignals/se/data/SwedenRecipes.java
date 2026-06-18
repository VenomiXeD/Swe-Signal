package venomized.mods.extendedsignals.se.data;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SwedenRecipes extends RecipeProvider {
    public SwedenRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    /**
     * @param p_recipeOutput
     * @param holderLookup
     */
    @Override
    protected void buildRecipes(RecipeOutput p_recipeOutput, HolderLookup.Provider holderLookup) {
        super.buildRecipes(p_recipeOutput, holderLookup);
    }
}
