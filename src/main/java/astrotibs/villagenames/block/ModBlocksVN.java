package astrotibs.villagenames.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.block.BlockPrismarine;
import astrotibs.villagenames.prismarine.block.BlockSeaLantern;
import astrotibs.villagenames.prismarine.block.BlockSpongeVN;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocksVN {

    // Prismarine
    public static final Block blockPrismarine = new BlockPrismarine();
    public static final Block blockSeaLantern = new BlockSeaLantern();

    // Sponge
    public static final Block blockSpongeVN = new BlockSpongeVN();

    public static void init() {
        if (GeneralConfig.addPrismarine) {
            GameRegistry.registerBlock(blockSeaLantern, "sea_lantern");
            GameRegistry
                .registerBlock(blockPrismarine, ((ISubBlocksBlock) blockPrismarine).getItemBlockClass(), "prismarine");

            // Oredict registries
            OreDictionary.registerOre("blockPrismarine", new ItemStack(blockPrismarine, 1, 0));
            OreDictionary.registerOre("blockPrismarineBrick", new ItemStack(blockPrismarine, 1, 1));
            OreDictionary.registerOre("blockPrismarineDark", new ItemStack(blockPrismarine, 1, 2));
        }

        if (GeneralConfig.addSponges) {
            GameRegistry.registerBlock(blockSpongeVN, ((ISubBlocksBlock) blockSpongeVN).getItemBlockClass(), "sponge");
        }
    }

    public static interface ISubBlocksBlock {

        Class<? extends ItemBlock> getItemBlockClass();
    }
}
