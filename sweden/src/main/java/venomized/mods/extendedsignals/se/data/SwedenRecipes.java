package venomized.mods.extendedsignals.se.data;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeSerializer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

import java.util.function.Consumer;

public class SwedenRecipes extends RecipeProvider {
    public SwedenRecipes(PackOutput pOutput) {
        super(pOutput);
    }

    /**
     * @param pWriter
     */
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // TODO: Recipes
        new SequencedAssemblyRecipeBuilder(ExtendedSignalsSweden.res("test"));
    }
}
