package astrotibs.villagenames.handler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ChestLootHandler {

    public static void iglooChest() {
        // From
        // https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation

        ChestGenHooks iglooChestContents = ChestGenHooks.getInfo("iglooChest"); // create registered ChestGenHooks
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 15));
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 15));
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.gold_nugget), 1, 3, 10));
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 2));
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.rotten_flesh), 1, 1, 10));
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.emerald), 1, 1, 1));
        iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.wheat), 2, 3, 10));

        iglooChestContents.setMin(2); // inclusive
        iglooChestContents.setMax(9); // exclusive

        ChestGenHooks iglooRareChestContents = ChestGenHooks.getInfo("iglooChestGoldapple"); // create registered
                                                                                             // ChestGenHooks
        iglooRareChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.golden_apple), 1, 1, 1));

        iglooRareChestContents.setMin(1); // inclusive
        iglooRareChestContents.setMax(1); // exclusive
    }
}
