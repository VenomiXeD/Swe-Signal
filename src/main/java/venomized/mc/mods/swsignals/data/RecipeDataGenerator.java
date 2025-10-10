package venomized.mc.mods.swsignals.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import venomized.mc.mods.swsignals.block.se.SeBlocks;
import venomized.mc.mods.swsignals.item.SwItems;

import java.util.function.Consumer;

public class RecipeDataGenerator extends RecipeProvider {
    public RecipeDataGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    protected static ShapedRecipeBuilder shapedRecipe(RecipeOutput output, String pattern) {
        ShapedRecipeBuilder recipeBuilder = ShapedRecipeBuilder
                .shaped(RecipeCategory.TRANSPORTATION, output.item, output.count);
        for (String s : pattern.split("\n")) {
            recipeBuilder.pattern(s + " ".repeat(3 - s.length()));
        }
        recipeBuilder.unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT));
        return recipeBuilder;
    }

    protected static void recursiveSignalRecipe(Item previousSignal, Item nextSignal, Consumer<FinishedRecipe> consumer) {
        shapedRecipe(new RecipeOutput(nextSignal),
                """
                        P  
                        E  
                        S  
                        """)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .define('E', AllItems.ELECTRON_TUBE.get())
                .define('S', previousSignal)
                .save(consumer);
    }

    /**
     * @param pWriter
     */
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_SIGNAL_BOX.asItem()),
                """
                        PEP
                        PEP
                        PRP
                        """)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .define('E', AllItems.ELECTRON_TUBE::asItem)
                .define('R', AllTags.forgeItemTag("dusts/redstone"))
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SwItems.SIGNAL_TUNER.asItem(), 1),
                """
                         L 
                         E 
                         P 
                        """)
                .define('L', AllBlocks.REDSTONE_LINK::asItem)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .define('E', AllItems.ELECTRON_TUBE::get)
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER.asItem(), 1),
                """
                        LPE
                        PPP
                        EA 
                        """)
                .define('L', AllBlocks.REDSTONE_LINK::asItem)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .save(pWriter);

        swedishRecipes(pWriter);
    }

    protected void swedishRecipes(Consumer<FinishedRecipe> pWriter) {
        // Each signal
        recursiveSignalRecipe(SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.asItem(), pWriter);
        recursiveSignalRecipe(SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.asItem(), pWriter);
        recursiveSignalRecipe(SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_FIVE_LIGHT_SIGNAL.asItem(), pWriter);

        // base signal (2l)
        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.asItem()),
                """
                        PEP
                        PEP
                         A 
                        """)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_MODERN_DWARF_SIGNAL.asItem()),
                """
                        EEP
                        EEP
                        AA 
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL.asItem(), 1),
                """
                        EEP
                        EEP
                        AAE
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_CROSSING_GATE.asItem(), 1),
                """
                        PPP
                        PAP
                        PPE
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_THREE_LIGHT_CROSSING_SIGNAL.asItem(), 1),
                """
                        EPE
                        PEP
                         A 
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .save(pWriter);

        shapedRecipe(new RecipeOutput(SeBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL.asItem(), 1),
                """
                        EPE
                         A
                         A 
                        """)
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .save(pWriter);
    }

    protected record RecipeOutput(Item item, int count) {
        public RecipeOutput(Item item) {
            this(item, 1);
        }
    }
}
