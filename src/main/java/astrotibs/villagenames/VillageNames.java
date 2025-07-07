package astrotibs.villagenames;

import java.io.File;

import net.minecraft.block.BlockDispenser;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.config.ConfigInit;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.handler.ChestLootHandler;
import astrotibs.villagenames.igloo.IglooGeneratorIWG;
import astrotibs.villagenames.igloo.VNComponentIglooPieces;
import astrotibs.villagenames.igloo.VNMapGenIgloo;
import astrotibs.villagenames.init.Recipes;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.particle.PacketHandlerClient;
import astrotibs.villagenames.prismarine.guardian.particle.SToCMessage;
import astrotibs.villagenames.prismarine.guardian.spawning.SpawnEventListener;
import astrotibs.villagenames.prismarine.monument.MonumentGeneratorIWG;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonument;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonumentPieces;
import astrotibs.villagenames.proxy.CommonProxy;
import astrotibs.villagenames.spawnegg.DispenserBehavior;
import astrotibs.villagenames.spawnegg.ItemSpawnEggVN;
import astrotibs.villagenames.spawnegg.SpawnEggRegistry;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

/*
 * Testing sword:
 * give @p golden_sword 1 0 {display:{Name:"Un-Instantiator"}, ench:[{id:16,lvl:1000},{id:34,lvl:99}]}
 * Loot level 3: {id:21,lvl:3}
 */

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.VERSION,
    guiFactory = Reference.GUI_FACTORY)
public final class VillageNames {

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy PROXY;

    public static SimpleNetworkWrapper VNNetworkWrapper; // Added from Dragon Artifacts

    public static File configDirectory;

    @Instance(Reference.MOD_ID)
    public static VillageNames instance;

    public static String currentConfigFolder = "OceanMonumentBackport";

    /*
     * The number of structures you need to use the Codex on to trigger the achievement.
     * If the player does not use any mods that add valid searchable structures,
     * AND they're using the 1.7 version of Village Names,
     * AND they're not using its optional Monument or Igloo generation,
     * then there are seven structures they can identify, so they have to identify them all.
     * The structures are:
     * Village
     * Desert Pyramid
     * Jungle Pyramid
     * Swamp Hut
     * Mineshaft
     * Stronghold
     * Nether Fortress
     */
    public static int numberStructuresArchaeologist = 7;

    public static ItemSpawnEggVN spawnEgg;

    // PRE-INIT
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDirectory = new File(event.getModConfigurationDirectory(), currentConfigFolder);
        ConfigInit.init(configDirectory);

        // Moved down here to make sure config fires first!?
        ModItems.init();
        ModBlocksVN.init();

        if (GeneralConfig.addOceanMonuments) {
            // Register Ocean Monument stuff here
            GameRegistry.registerWorldGenerator(new MonumentGeneratorIWG(), 0);
            MapGenStructureIO.registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
            StructureOceanMonumentPieces.registerOceanMonumentPieces();

            // Guardian registry has been moved to init()

            // Spawn egg stuff
            spawnEgg = new ItemSpawnEggVN();
            GameRegistry.registerItem(spawnEgg, spawnEgg.getUnlocalizedName());
            BlockDispenser.dispenseBehaviorRegistry.putObject(spawnEgg, new DispenserBehavior());

            MinecraftForge.EVENT_BUS.register(new SpawnEventListener());
            LogHelper.info("Registered Guardians and ocean monuments");
        }

        if (GeneralConfig.addIgloos) {
            GameRegistry.registerWorldGenerator(new IglooGeneratorIWG(), 0);
            MapGenStructureIO.registerStructure(VNMapGenIgloo.Start.class, "Temple");
            VNComponentIglooPieces.registerScatteredFeaturePieces();
            ChestLootHandler.iglooChest();
            LogHelper.info("Registered Igloo generation");
        }

        PROXY.preInit(event);

        // Establish the channel
        VNNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_CHANNEL);

        // Register different messages here

        int messageID = 0;

        VNNetworkWrapper.registerMessage(PacketHandlerClient.class, SToCMessage.class, messageID++, Side.CLIENT);

        // The following overrides the mcmod.info file!
        // Adapted from Jabelar's Magic Beans:
        // https://github.com/jabelar/MagicBeans-1.7.10/blob/e48456397f9c6c27efce18e6b9ad34407e6bc7c7/src/main/java/com/blogspot/jabelarminecraft/magicbeans/MagicBeans.java
        event.getModMetadata().autogenerated = false; // stops it from complaining about missing mcmod.info

        event.getModMetadata().name = // name
            EnumChatFormatting.GOLD + Reference.MOD_NAME + EnumChatFormatting.RESET;

        event.getModMetadata().version = // version
            Reference.VERSION;

        event.getModMetadata().credits = // credits
            EnumChatFormatting.AQUA + "Thanks: Pahimar, MineMaarten, whrrgarbl, Jabelar, Darian Stephens"
                + EnumChatFormatting.RESET;

        event.getModMetadata().authorList.clear();
        event.getModMetadata().authorList.add( // authorList - added as a list
            EnumChatFormatting.BLUE + "AstroTibs" + EnumChatFormatting.RESET);

        event.getModMetadata().url = // project URL
            EnumChatFormatting.GRAY + Reference.URL + EnumChatFormatting.RESET;

        event.getModMetadata().description = // description
            EnumChatFormatting.GREEN + "Backports Ocean Monuments and Igloos." + EnumChatFormatting.RESET;

        event.getModMetadata().logoFile = "assets/villagenames/vn_banner.png";
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        if (GeneralConfig.addOceanMonuments) {
            // Register Guardian stuff here
            int entityIDs = 0; // Increment this to make sure everything has its own model register
            // EntityRegistry.registerGlobalEntityID(EntityGuardian.class, GeneralConfig.alternateGuardianNamespace ?
            // "Guardian_VN" : "Guardian", EntityRegistry.findGlobalUniqueEntityId(), 0x5A7A6C, 0xE57E3E);
            EntityRegistry
                .registerModEntity(EntityGuardian.class, Reference.MOB_GUARDIAN_VN, entityIDs++, this, 64, 3, true);
            // RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
        }

        // register crafting recipes
        Recipes.init();

        // Renderers
        PROXY.init(event);
        PROXY.registerRender();
        PROXY.registerEvents();
    }

    // POST-INIT
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SpawnEggRegistry.addAllSpawnEggs();
    }
}
