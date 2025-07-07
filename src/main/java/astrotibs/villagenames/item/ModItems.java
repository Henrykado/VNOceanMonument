package astrotibs.villagenames.item;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.item.PrismarineCrystals;
import astrotibs.villagenames.prismarine.item.PrismarineShard;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

    // Prismarine
    public static final Item prismarine_shard = new PrismarineShard();
    public static final Item prismarine_crystals = new PrismarineCrystals();

    public static void init() {
        // Prismarine
        if (GeneralConfig.addPrismarine) {
            if (!Loader.isModLoaded("botania")) {
                GameRegistry.registerItem(prismarine_shard, "prismarine_shard");
                OreDictionary.registerOre("gemPrismarine", prismarine_shard);
            }

            GameRegistry.registerItem(prismarine_crystals, "prismarine_crystals");
            OreDictionary.registerOre("dustPrismarine", prismarine_crystals);
        }
    }
}
