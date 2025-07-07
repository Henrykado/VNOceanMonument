package astrotibs.villagenames.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import astrotibs.villagenames.utility.Reference;

public class GeneralConfig {

    public static ConfigurationVN config;

    // public static String[] blackList;
    public static boolean wellSlabs;

    public static boolean addPrismarine;
    public static boolean addSponges;
    public static boolean addOceanMonuments;
    // public static boolean alternateGuardianNamespace;
    public static boolean addIgloos;
    public static boolean biomedictIgloos;

    public static boolean debugMessages;

    public static void init(File configFile) {
        if (config == null) {
            config = new ConfigurationVN(configFile);
            loadConfiguration();
        }
    }

    protected static void loadConfiguration() {

        // --- General --- //
        wellSlabs = config.getBoolean(
            "Well slabs",
            Reference.CATEGORY_GENERAL,
            false,
            "Replace the cobblestone rims of wells with stone slabs, making it easier for players and villagers to escape if they fall in.");

        // --------------Miscellaneous-----------------//
        addPrismarine = config.getBoolean(
            "Add Prismarine",
            Reference.CATEGORY_MISCELLANEOUS,
            true,
            "Adds Prismarine blocks and items. Very difficult to acquire if \"Add Monuments\" is false.\nIf Botania is installed the Prismarine blocks and items from that mod will automatically replace the ones from this mod.");

        addSponges = config.getBoolean("Add Sponges", Reference.CATEGORY_MISCELLANEOUS, true, "Adds absorbent Sponges");
        addOceanMonuments = config.getBoolean(
            "Add Monuments",
            Reference.CATEGORY_MISCELLANEOUS,
            true,
            "Generate Ocean Monuments and Guardians. Pairs well with \"Add Prismarine\" if you're not using another mod to backport prismarine.");
        // alternateGuardianNamespace = config.getBoolean("Alternate Guardian Namespace",
        // Reference.CATEGORY_MISCELLANEOUS, false, "WARNING: TOGGLING THIS VALUE WILL REMOVE OR REPLACE ALL
        // CURRENTLY-SPAWNED GUARDIANS FROM YOUR WORLD, INCLUDING ELDERS!\n"+"Set this to true if you have fatal
        // conflicts loading another mod using the entity name minecraft:Guardian by instead using the name
        // minecraft:Guardian_VN.");
        addIgloos = config
            .getBoolean("Add Igloos", Reference.CATEGORY_MISCELLANEOUS, false, "Generate Igloos from 1.9+");
        biomedictIgloos = config.getBoolean(
            "Allow Igloos in modded biomes",
            Reference.CATEGORY_MISCELLANEOUS,
            false,
            "Igloos can generate in mods' snowy plains biomes, rather than just vanilla's Ice Plains and Cold Taiga");

        if (config.hasChanged()) config.save();

    }

    /**
     * Inputs a (Profession|ID|vanillaType) String list and breaks it into three array lists
     */
    public static Map<String, List> unpackMappedProfessions(String[] inputList) {
        List<String> otherModProfessions = new ArrayList<String>();
        List<Integer> otherModIDs = new ArrayList<Integer>();
        List<Integer> vanillaProfMaps = new ArrayList<Integer>();

        for (String entry : inputList) {
            entry.replaceAll("/", ""); // Forward slashses don't need to be escaped
            entry.replaceAll("\\\\", ""); // \ is BOTH String and regex; needs to be double-escaped. See
                                          // https://stackoverflow.com/questions/1701839/string-replaceall-single-backslashes-with-double-backslashes
            entry.replaceAll("..", "");
            // Split by pipe
            String[] splitEntry = entry.split("\\|");

            // Initialize temp fields
            String otherModProfession = "";
            int otherModID = -1;
            int vanillaProfMap = -1;

            // Place entries into variables
            try {
                otherModProfession = splitEntry[0].trim();
            } catch (Exception e) {
                otherModProfession = "";
            }
            try {
                otherModID = Integer.parseInt(splitEntry[1].trim());
            } catch (Exception e) {
                otherModID = -1;
            }
            try {
                vanillaProfMap = Integer.parseInt(splitEntry[2].trim());
            } catch (Exception e) {
                vanillaProfMap = -1;
            }

            if (!otherModProfession.equals("") && otherModID != -1) {
                otherModProfessions.add(otherModProfession);
                otherModIDs.add(otherModID);
                vanillaProfMaps.add(vanillaProfMap);
            }
        }

        Map<String, List> map = new HashMap();
        map.put("Professions", otherModProfessions);
        map.put("IDs", otherModIDs);
        map.put("VanillaProfMaps", vanillaProfMaps);

        return map;
    }

    /**
     * Loads the (nameType|structureType|structureTitle|dimensionName|bookType|entityClassPath) string lists from
     * othermods.cfg > Mod Structures
     * and assigns them to this instance's variables.
     */
    public static Map<String, List> unpackModStructures(String[] inputList) {

        List<String> otherModNameTypes = new ArrayList<String>();
        List<String> otherModStructureTypes = new ArrayList<String>();
        List<String> otherModStructureTitles = new ArrayList<String>();
        List<String> otherModDimensionNames = new ArrayList<String>();
        List<String> otherModBookTypes = new ArrayList<String>();
        List<String> otherModClassPaths = new ArrayList<String>();

        for (String entry : inputList) {
            // Remove parentheses
            entry.replaceAll("\\)", "");
            entry.replaceAll("\\(", "");
            // Split by pipe
            String[] splitEntry = entry.split("\\|");

            // Initialize temp fields
            String otherModNameType = "";
            String otherModStructureType = "FAILSAFE";
            String otherModStructureTitle = "";
            String otherModDimensionName = "";
            String otherModBookType = "";
            String otherModClassPath = "";

            // Place entries into variables
            try {
                otherModNameType = splitEntry[0].trim();
            } catch (Exception e) {
                otherModNameType = "";
            }
            try {
                otherModStructureType = splitEntry[1].trim();
            } catch (Exception e) {
                otherModStructureType = "FAILSAFE";
            }
            try {
                otherModStructureTitle = splitEntry[2].trim();
            } catch (Exception e) {
                otherModStructureTitle = "";
            }
            try {
                otherModDimensionName = splitEntry[3].trim();
            } catch (Exception e) {
                otherModDimensionName = "";
            }
            try {
                otherModBookType = splitEntry[4].trim();
            } catch (Exception e) {
                otherModBookType = "";
            }
            try {
                otherModClassPath = splitEntry[5].trim();
            } catch (Exception e) {
                otherModClassPath = "";
            }

            if (!otherModNameType.equals("") && !otherModStructureType.equals("") && !otherModBookType.equals("")) { // Something
                                                                                                                     // was
                                                                                                                     // actually
                                                                                                                     // assigned
                                                                                                                     // in
                                                                                                                     // the
                                                                                                                     // try
                                                                                                                     // block
                otherModNameTypes.add(otherModNameType);
                otherModStructureTypes.add(otherModStructureType);
                otherModStructureTitles.add(otherModStructureTitle);
                otherModDimensionNames.add(otherModDimensionName);
                otherModBookTypes.add(otherModBookType);
                otherModClassPaths.add(otherModClassPath);
            }
        }

        Map<String, List> map = new HashMap();
        map.put("NameTypes", otherModNameTypes);
        map.put("StructureTypes", otherModStructureTypes);
        map.put("StructureTitles", otherModStructureTitles);
        map.put("DimensionNames", otherModDimensionNames);
        map.put("BookTypes", otherModBookTypes);
        map.put("ClassPaths", otherModClassPaths);

        return map;
    }

    /**
     * Loads the (nameType|profession|classPath|AddOrRemove) string lists from othermods.cfg > Automatic Names and
     * othermods.cfg > Clickable Names
     * and assigns them to this instance's variables.
     */
    public static Map<String, List> unpackMappedNames(String[] inputList) {

        List<String> otherModNameTypes = new ArrayList<String>();
        List<String> otherModProfessions = new ArrayList<String>();
        List<String> otherModClassPaths = new ArrayList<String>();
        List<String> addOrRemoveA = new ArrayList<String>();

        for (String entry : inputList) {
            // Remove parentheses
            entry.replaceAll("\\)", "");
            entry.replaceAll("\\(", "");
            // Split by pipe
            String[] splitEntry = entry.split("\\|");

            // Initialize temp fields
            String otherModNameType = "";
            String otherModProfession = "";
            String otherModClassPath = "";
            String addOrRemove = "";

            // Place entries into variables
            try {
                otherModNameType = splitEntry[0].trim();
            } catch (Exception e) {
                otherModNameType = "";
            }
            try {
                otherModProfession = splitEntry[1].trim();
            } catch (Exception e) {
                otherModProfession = "";
            }
            try {
                otherModClassPath = splitEntry[2].trim();
            } catch (Exception e) {
                otherModClassPath = "";
            }
            try {
                addOrRemove = splitEntry[3].trim();
            } catch (Exception e) {
                addOrRemove = "";
            }

            if (!otherModClassPath.equals("") && !otherModNameType.equals("")) { // Something was actually assigned in
                                                                                 // the try block

                otherModClassPaths.add(otherModClassPath);
                otherModNameTypes.add(otherModNameType);
                otherModProfessions.add(otherModProfession);
                addOrRemoveA.add(addOrRemove);

            }
        }

        Map<String, List> map = new HashMap();
        map.put("NameTypes", otherModNameTypes);
        map.put("Professions", otherModProfessions);
        map.put("ClassPaths", otherModClassPaths);
        map.put("AddOrRemove", addOrRemoveA);

        return map;
    }

    /**
     * Loads the (group|classPath|unlocName|meta) string lists and assigns them to this instance's variables.
     */
    public static Map<String, List> unpackZombieCureCatalysts(String[] inputList) {
        List<String> zombieCureCatalystGroups = new ArrayList<String>();
        List<String> zombieCureCatalystClassPaths = new ArrayList<String>();
        List<String> zombieCureCatalystUnlocNames = new ArrayList<String>();
        List<Integer> zombieCureCatalystMetas = new ArrayList<Integer>();

        for (String entry : inputList) {
            // Remove parentheses
            entry.replaceAll("\\)", "");
            entry.replaceAll("\\(", "");
            // Split by pipe
            String[] splitEntry = entry.split("\\|");

            // Initialize temp fields
            String zombieCureCatalystGroup = "";
            String zombieCureCatalystClassPath = "";
            String zombieCureCatalystUnlocName = "";
            int zombieCureCatalystMeta = -1;

            // Place entries into variables
            try {
                zombieCureCatalystGroup = splitEntry[0].trim();
            } catch (Exception e) {}
            try {
                zombieCureCatalystClassPath = splitEntry[1].trim();
            } catch (Exception e) {}
            try {
                zombieCureCatalystUnlocName = splitEntry[2].trim();
            } catch (Exception e) {}
            try {
                zombieCureCatalystMeta = Integer.parseInt(splitEntry[3].trim());
            } catch (Exception e) {}

            if (!zombieCureCatalystGroup.equals("") && !zombieCureCatalystClassPath.equals("")) { // Something was
                                                                                                  // actually assigned
                                                                                                  // in the try block
                zombieCureCatalystGroups.add(zombieCureCatalystGroup);
                zombieCureCatalystClassPaths.add(zombieCureCatalystClassPath);
                zombieCureCatalystUnlocNames.add(zombieCureCatalystUnlocName);
                zombieCureCatalystMetas.add(zombieCureCatalystMeta);
            }
        }

        Map<String, List> map = new HashMap();
        map.put("Groups", zombieCureCatalystGroups);
        map.put("ClassPaths", zombieCureCatalystClassPaths);
        map.put("UnlocNames", zombieCureCatalystUnlocNames);
        map.put("Metas", zombieCureCatalystMetas);

        return map;
    }

    /**
     * Loads the (group|speedup|limit) string lists and assigns them to this instance's variables.
     */
    public static Map<String, List> unpackZombieCureGroups(String[] inputList) {
        List<String> zombieCureGroupGroups = new ArrayList<String>();
        List<Double> zombieCureGroupSpeedups = new ArrayList<Double>();
        List<Integer> zombieCureGroupLimits = new ArrayList<Integer>();

        for (String entry : inputList) {
            // Remove parentheses
            entry.replaceAll("\\)", "");
            entry.replaceAll("\\(", "");
            // Split by pipe
            String[] splitEntry = entry.split("\\|");

            // Initialize temp fields
            String zombieCureGroupGroup = "";
            double zombieCureGroupSpeedup = 0.0D;
            int zombieCureGroupLimit = -1;

            // Place entries into variables
            try {
                zombieCureGroupGroup = splitEntry[0].trim();
            } catch (Exception e) {}
            try {
                zombieCureGroupSpeedup = Double.parseDouble(splitEntry[1].trim());
            } catch (Exception e) {}
            try {
                zombieCureGroupLimit = Integer.parseInt(splitEntry[2].trim());
            } catch (Exception e) {}

            if (!zombieCureGroupGroup.equals("")) { // Something was actually assigned in the try block
                zombieCureGroupGroups.add(zombieCureGroupGroup);
                zombieCureGroupSpeedups.add(zombieCureGroupSpeedup);
                zombieCureGroupLimits.add(zombieCureGroupLimit);
            }
        }

        Map<String, List> map = new HashMap();
        map.put("Groups", zombieCureGroupGroups);
        map.put("Speedups", zombieCureGroupSpeedups);
        map.put("Limits", zombieCureGroupLimits);

        return map;
    }

    /**
     * Loads the (careerAsset|zombieCareerAsset|professionID) string lists and assigns them to this instance's
     * variables.
     */
    public static Map<String, List> unpackModVillagerSkins(String[] inputList) {
        List<String> careerAsset_a = new ArrayList<String>();
        List<String> zombieCareerAsset_a = new ArrayList<String>();
        List<Integer> professionID_a = new ArrayList<Integer>();

        for (String entry : inputList) {
            // Remove slashes and double dots to prevent address abuse
            entry.replaceAll("/", ""); // Forward slashses don't need to be escaped
            entry.replaceAll("\\\\", ""); // \ is BOTH String and regex; needs to be double-escaped. See
                                          // https://stackoverflow.com/questions/1701839/string-replaceall-single-backslashes-with-double-backslashes
            entry.replaceAll("..", "");
            // Split by pipe
            String[] splitEntry = entry.split("\\|");

            // Initialize temp fields
            String careerAsset = "";
            String zombieCareerAsset = "";
            Integer professionID = -1;

            // Place entries into variables
            try {
                careerAsset = splitEntry[0].trim();
            } catch (Exception e) {
                careerAsset = "";
            }
            try {
                zombieCareerAsset = splitEntry[1].trim();
            } catch (Exception e) {
                zombieCareerAsset = "";
            }
            try {
                professionID = Integer.valueOf(splitEntry[2].trim());
            } catch (Exception e) {
                professionID = -1;
            }

            if (!careerAsset.equals("")) { // Something was actually assigned in the try block
                careerAsset_a.add(careerAsset);
                zombieCareerAsset_a.add(zombieCareerAsset);
                professionID_a.add(professionID);
            }
        }

        Map<String, List> map = new HashMap();
        map.put("careerAsset", careerAsset_a);
        map.put("zombieCareerAsset", zombieCareerAsset_a);
        map.put("professionID", professionID_a);

        return map;
    }

}
