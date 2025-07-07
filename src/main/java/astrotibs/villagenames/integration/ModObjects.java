package astrotibs.villagenames.integration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.item.ModItems;
import cpw.mods.fml.common.Loader;

public class ModObjects {

    // Constantly referenced domain names
    public static final String DOM_BOTANIA = "Botania";
    public static final String DOM_ETFUTURUM = "etfuturum";

    // Sponge
    public static final String sponge_EF = DOM_ETFUTURUM + ":sponge";
    // Prismarine
    public static final String prismarine_Bo = DOM_BOTANIA + ":prismarine"; // Meta - 0:regular, 1:bricks, 2:dark
    public static final String prismarine_EF = DOM_ETFUTURUM + ":prismarine_block"; // Meta - 0:regular, 1:bricks,
                                                                                    // 2:dark
    // Prismarine Stairs
    public static final String prismarineStairs_Bo = DOM_BOTANIA + ":prismarine0Stairs";
    public static final String prismarineStairs_EF = DOM_ETFUTURUM + ":prismarine_stairs";
    // Prismarine Slab
    public static final String prismarineSlab_Bo = DOM_BOTANIA + ":prismarine0Slab";
    public static final String prismarineSlab_EF = DOM_ETFUTURUM + ":prismarine_slab";
    // Prismarine Wall
    public static final String prismarineWall_Bo = DOM_BOTANIA + ":prismarine0Wall";
    public static final String prismarineWall_EF = DOM_ETFUTURUM + ":prismarine_wall";

    // Prismarine
    public static final String manaResource_Bo = DOM_BOTANIA + ":manaResource"; // meta 10
    public static final String prismarineShard_EF = DOM_ETFUTURUM + ":prismarine_shard";
    // Prismarine Crystals
    public static final String prismarineCrystal_EF = DOM_ETFUTURUM + ":prismarine_crystals";

    public static ItemStack chooseModPrismarineCrystalsItemStack() {
        if (GeneralConfig.addPrismarine) {
            return new ItemStack(ModItems.prismarine_crystals);
        }

        Item moditem;
        moditem = getItemFromName(prismarineCrystal_EF);
        if (moditem != null) {
            return new ItemStack(moditem);
        }

        return null;
    }

    public static ItemStack chooseModPrismarineShardItemStack() {
        if (GeneralConfig.addPrismarine && !Loader.isModLoaded("botania")) {
            return new ItemStack(ModItems.prismarine_shard);
        }

        Item moditem = getItemFromName(ModObjects.prismarine_Bo);
        if (moditem != null) {
            return new ItemStack(moditem);
        }
        moditem = getItemFromName(ModObjects.prismarine_EF);
        if (moditem != null) {
            return new ItemStack(moditem);
        }

        return null;
    }

    public static Object[] chooseModSpongeBlockObject(boolean isWet) {
        if (GeneralConfig.addPrismarine) {
            return new Object[] { ModBlocksVN.blockSpongeVN, isWet ? 1 : 0 };
        }

        Block modblock = Block.getBlockFromName(ModObjects.sponge_EF);
        if (modblock != null) {
            return new Object[] { modblock, isWet ? 1 : 0 };
        }

        return null;
    }

    public static Object[] chooseModPrismarineBlockObject() {
        if (GeneralConfig.addPrismarine && !Loader.isModLoaded("botania")) {
            return new Object[] { ModBlocksVN.blockPrismarine, 0 };
        }

        Block modblock = Block.getBlockFromName(ModObjects.prismarine_Bo);
        if (modblock != null) {
            return new Object[] { modblock, 0 };
        }
        modblock = Block.getBlockFromName(ModObjects.prismarine_EF);
        if (modblock != null) {
            return new Object[] { modblock, 0 };
        }

        return null;
    }

    public static Object[] chooseModPrismarineBricksObject() {
        if (GeneralConfig.addPrismarine && !Loader.isModLoaded("botania")) {
            return new Object[] { ModBlocksVN.blockPrismarine, 1 };
        }

        Block modblock = Block.getBlockFromName(ModObjects.prismarine_Bo);
        if (modblock != null) {
            return new Object[] { modblock, 1 };
        }
        modblock = Block.getBlockFromName(ModObjects.prismarine_EF);
        if (modblock != null) {
            return new Object[] { modblock, 1 };
        }

        return null;
    }

    public static Object[] chooseModDarkPrismarineObject() {
        if (GeneralConfig.addPrismarine && !Loader.isModLoaded("botania")) {
            return new Object[] { ModBlocksVN.blockPrismarine, 2 };
        }

        Block modblock = Block.getBlockFromName(ModObjects.prismarine_Bo);
        if (modblock != null) {
            return new Object[] { modblock, 2 };
        }
        modblock = Block.getBlockFromName(ModObjects.prismarine_EF);
        if (modblock != null) {
            return new Object[] { modblock, 2 };
        }

        return null;
    }

    public static Block chooseModSeaLanternBlock() {
        if (GeneralConfig.addPrismarine && !Loader.isModLoaded("botania")) {
            return ModBlocksVN.blockSeaLantern;
        }

        Block modblock = Block.getBlockFromName(ModObjects.prismarine_Bo);
        if (modblock != null) {
            return modblock;
        }
        modblock = Block.getBlockFromName(ModObjects.prismarine_EF);
        if (modblock != null) {
            return modblock;
        }

        return null;
    }

    public static Item getItemFromName(String itemName) {
        if (Item.itemRegistry.containsKey(itemName)) {
            return (Item) Item.itemRegistry.getObject(itemName);
        } else {
            try {
                return (Item) Item.itemRegistry.getObjectById(Integer.parseInt(itemName));
            } catch (NumberFormatException numberformatexception) {
                return null;
            }
        }
    }
}
