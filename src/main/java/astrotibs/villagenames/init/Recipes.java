package astrotibs.villagenames.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.item.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {

    public static void init() {
        if (GeneralConfig.addPrismarine) {
            OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.prismarine_shard));
            OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.prismarine_crystals));
            OreDictionary.registerOre(
                "blockPrismarine",
                new ItemStack(ModBlocksVN.blockPrismarine, 1, OreDictionary.WILDCARD_VALUE));

            int rough = 0;
            int bricks = 1;
            int dark = 2;

            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockPrismarine, 1, dark),
                    "xxx",
                    "xyx",
                    "xxx",
                    'x',
                    "shardPrismarine",
                    'y',
                    "dyeBlack"));
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockPrismarine, 1, rough),
                    "xx",
                    "xx",
                    'x',
                    "shardPrismarine"));
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockPrismarine, 1, bricks),
                    "xxx",
                    "xxx",
                    "xxx",
                    'x',
                    "shardPrismarine"));
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockSeaLantern),
                    "xyx",
                    "yyy",
                    "xyx",
                    'x',
                    "shardPrismarine",
                    'y',
                    "crystalPrismarine"));
        }

        if (GeneralConfig.addSponges) {
            // Sponge stuff
            GameRegistry.addSmelting(
                new ItemStack(ModBlocksVN.blockSpongeVN, 1, 1),
                new ItemStack(ModBlocksVN.blockSpongeVN, 1, 0),
                0.0F);
            // Allow vanilla sponge to be crafted into functional sponge and vice-versa
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(new ItemStack(Blocks.sponge), new ItemStack(ModBlocksVN.blockSpongeVN, 1, 0)));
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(new ItemStack(ModBlocksVN.blockSpongeVN, 1, 0), new ItemStack(Blocks.sponge)));
        }
    }
}
