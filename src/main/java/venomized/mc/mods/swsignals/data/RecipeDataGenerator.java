package venomized.mc.mods.swsignals.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.se.SeBlocks;

import java.util.function.Consumer;

public class RecipeDataGenerator extends RecipeProvider {

    public RecipeDataGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    protected static void recursiveSignalRecipe(Item previousSignal, Item nextSignal, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.TRANSPORTATION,
                        nextSignal
                ).unlockedBy(getHasName(AllItems.ELECTRON_TUBE.get()), has(AllItems.ELECTRON_TUBE.get()))
                .pattern(" P ")
                .pattern(" E ")
                .pattern(" S ")
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
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, SeBlocks.BLOCK_SIGNAL_BOX.get())
                .unlockedBy(getHasName(SeBlocks.BLOCK_SIGNAL_BOX.asItem()), has(AllTags.forgeItemTag("dusts/redstone")))
                .pattern("pep").pattern("pep").pattern("prp")
                .define('p', AllTags.forgeItemTag("plates/iron"))
                .define('e', AllItems.ELECTRON_TUBE::asItem)
                .define('r', AllTags.forgeItemTag("dusts/redstone"))
                .save(pWriter);

        swedishRecipes(pWriter);
    }

    protected void swedishRecipes(Consumer<FinishedRecipe> pWriter) {
        // Each signal
        recursiveSignalRecipe(SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.asItem(), pWriter);
        recursiveSignalRecipe(SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.asItem(), pWriter);
        recursiveSignalRecipe(SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.asItem(), SeBlocks.BLOCK_FIVE_LIGHT_SIGNAL.asItem(), pWriter);

        // base signal (2l)
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION,SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.asItem())
                .unlockedBy(getHasName(AllBlocks.TRACK.asItem()), has(AllBlocks.TRACK.asItem()))
                .pattern("PEP")
                .pattern("PEP")
                .pattern(" A ")
                .define('P', AllTags.forgeItemTag("plates/iron"))
                .define('E', AllItems.ELECTRON_TUBE::get)
                .define('A', AllItems.ANDESITE_ALLOY::get)
                .save(pWriter);
    }
}
