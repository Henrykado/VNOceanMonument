package astrotibs.villagenames.config;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;

public class ConfigInit {

    public static void init(File configDirectory) {
        GeneralConfig.init(new File(configDirectory, "general.cfg"));
        MinecraftForge.EVENT_BUS.register(new GeneralConfig());
    }
}
