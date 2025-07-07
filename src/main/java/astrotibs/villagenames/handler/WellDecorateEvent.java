package astrotibs.villagenames.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import astrotibs.villagenames.config.GeneralConfig;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Adapted from BetterVillages by GoToLink:
 * https://github.com/GotoLink/BetterVillages/blob/aae028988da827d9cfd1b98b44b42ba9802ab9a2/bettervillages/BetterVillages.java
 * 
 * @author AstroTibs
 */
public class WellDecorateEvent {

    @SubscribeEvent
    public void onPopulating(PopulateChunkEvent.Post event) {

        if (event.hasVillageGenerated && event.world.provider.dimensionId == 0 && !event.world.isRemote) {

            Random random = event.world.rand;

            int i = (event.chunkX << 4) + 8;// Villages are offset
            int k = (event.chunkZ << 4) + 8;
            int y;
            Block id;
            Block id2;
            Block id3;
            int[] field;
            int[] field2;
            int[] field3;
            List<int[]> listOpaque;
            List<int[]> listWater;
            List<int[]> listWater2;
            List<int[]> listWater3;

            int isWellCorner = 0;
            for (int x = i; x < i + 16; x++) {
                for (int z = k; z < k + 16; z++) {// Search within chunk
                    y = event.world.getHeightValue(x, z);// block on top of a "solid" block
                    if (y > 1) {
                        y--;
                        id = event.world.getBlock(x, y, z);
                        while (id.isAir(event.world, x, y, z) || id.isLeaves(event.world, x, y, z)) {
                            y--;
                            id = event.world.getBlock(x, y, z);
                        }
                        // This part decorates wells!
                        if (id.isOpaqueCube()) {// found solid block in open air
                            id = event.world.getBlock(x, y - 4, z);
                            id2 = event.world.getBlock(x, y - 5, z);
                            id3 = event.world.getBlock(x, y - 6, z);
                            if (isWaterId(id) && isWaterId(id2) && isWaterId(id3)) {// found water under solid block
                                                                                    // layer
                                y -= 4;
                                field = new int[] { x, y, z };
                                field2 = new int[] { x, y - 1, z };
                                field3 = new int[] { x, y - 2, z };
                                listWater = getBorder(event.world, id, field);
                                listWater2 = getBorder(event.world, id2, field2);
                                listWater3 = getBorder(event.world, id3, field3);
                                listOpaque = getOpaqueBorder(event.world, field);
                                if (listWater.size() == 3 && listWater2.size() == 3
                                    && listWater3.size() == 3
                                    && listOpaque.size() == 5) {// found 3 water blocks AND 5 opaque blocks surrounding
                                                                // one water block on THREE levels, assuming this is a
                                                                // village well

                                    if (GeneralConfig.wellSlabs) {
                                        field = listOpaque.remove(1);
                                        event.world.setBlock(field[0], field[1] + 1, field[2], Blocks.stone_slab);
                                        field = listOpaque.remove(2);
                                        event.world.setBlock(field[0], field[1] + 1, field[2], Blocks.stone_slab);
                                    }

                                    while (event.world.getBlock(x, y, z) == id) {
                                        y--;
                                    }
                                    field = new int[] { x, y, z };
                                    listOpaque = getOpaqueBorder(event.world, field);

                                }
                            }
                            continue;
                        }
                    }
                }
            }
        }
    }

    private static List<int[]> getBorder(World world, Block id, int[] field) {
        List<int[]> list = new ArrayList<int[]>();
        for (int x = field[0] - 1; x < field[0] + 2; x++) {
            for (int z = field[2] - 1; z < field[2] + 2; z++) {
                if ((x != field[0] || z != field[2]) && world.getBlock(x, field[1], z) == id)
                    list.add(new int[] { x, field[1], z });
            }
        }
        return list;
    }

    private static List<int[]> getOpaqueBorder(World world, int[] field) {
        List<int[]> list = new ArrayList<int[]>();
        for (int x = field[0] - 1; x < field[0] + 2; x++) {
            for (int z = field[2] - 1; z < field[2] + 2; z++) {
                if ((x != field[0] || z != field[2]) && world.getBlock(x, field[1], z)
                    .isOpaqueCube()) list.add(new int[] { x, field[1], z });
            }
        }
        return list;
    }

    private static boolean isWaterId(Block id) {
        return id.getMaterial() == Material.water;
    }
}
