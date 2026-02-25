package venomized.mc.mods.swsignals.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import venomized.mc.mods.swsignals.block.se.SeBlocks;
import venomized.mc.mods.swsignals.item.SwItems;

import java.util.concurrent.CompletableFuture;

public class RecipeDataGenerator extends RecipeProvider {
    public RecipeDataGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries);
    }

    protected static ShapedRecipeBuilder shapedRecipe(RecipeResult output, String pattern) {
        ShapedRecipeBuilder recipeBuilder = ShapedRecipeBuilder
                .shaped(RecipeCategory.TRANSPORTATION, output.item, output.count);
        for (String s : pattern.split("\n")) {
            recipeBuilder.pattern(s + " ".repeat(3 - s.length()));
        }
        recipeBuilder.unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT));
        return recipeBuilder;
    }

    protected static void recursiveSignalRecipe(Item previousSignal, Item nextSignal, RecipeOutput output) {
        shapedRecipe(new RecipeResult(nextSignal),
                """
                        P  
                        E  
                        S  
                        """)
                .define('P', CommonMetal.IRON.plates)
                .define('E', AllItems.ELECTRON_TUBE.get())
                .define('S', previousSignal)
                .save(output);
    }

    /**
     * @param output
     */
    @Override
    protected void buildRecipes(RecipeOutput output) {
        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_SIGNAL_BOX.asItem()),
                """
                        PEP
                        PEP
                        PRP
                        """)
                .define('P', CommonMetal.IRON.plates)
                .define('E', AllItems.ELECTRON_TUBE::asItem)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .save(output);

        shapedRecipe(new RecipeResult(SwItems.SIGNAL_TUNER.asItem(), 1),
                """
                         L 
                         E 
                         P 
                        """)
                .define('L', AllBlocks.REDSTONE_LINK::asItem)
                .define('P', CommonMetal.IRON.plates)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .save(output);

        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER.asItem(), 1),
                """
                        LPE
                        PPP
                        EA 
                        """)
                .define('L', AllBlocks.REDSTONE_LINK::asItem)
                .define('P', CommonMetal.IRON.plates)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .save(output);

        swedishRecipes(output);
    }

    protected void swedishRecipes(RecipeOutput pWriter) {
        // Each signal
        recursiveSignalRecipe(SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.asItem(), pWriter);
        recursiveSignalRecipe(SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.asItem(), pWriter);
        recursiveSignalRecipe(SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_FIVE_LIGHT_SIGNAL.asItem(), pWriter);

        // base signal (2l)
        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.asItem()),
                """
                        PEP
                        PEP
                         A 
                        """)
                .define('P', CommonMetal.IRON.plates)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .save(pWriter);

        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_MODERN_DWARF_SIGNAL.asItem()),
                """
                        EEP
                        EEP
                        AA 
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', CommonMetal.IRON.plates)
                .save(pWriter);

        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL.asItem(), 1),
                """
                        EEP
                        EEP
                        AAE
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', CommonMetal.IRON.plates)
                .save(pWriter);

        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_CROSSING_GATE.asItem(), 1),
                """
                        PPP
                        PAP
                        PPE
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', CommonMetal.IRON.plates)
                .save(pWriter);

        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_THREE_LIGHT_CROSSING_SIGNAL.asItem(), 1),
                """
                        EPE
                        PEP
                         A 
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', CommonMetal.IRON.plates)
                .save(pWriter);

        shapedRecipe(new RecipeResult(SeBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL.asItem(), 1),
                """
                        EPE
                         A
                         A 
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', CommonMetal.IRON.plates)
                .save(pWriter);
    }

    protected record RecipeResult(Item item, int count) {
        public RecipeResult(Item item) {
            this(item, 1);
        }
    }
}
