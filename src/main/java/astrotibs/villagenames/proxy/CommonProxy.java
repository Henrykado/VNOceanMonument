package astrotibs.villagenames.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import astrotibs.villagenames.config.ConfigReloader;
import astrotibs.villagenames.handler.WellDecorateEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    protected Configuration config;

    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
        // Event listeners
        MinecraftForge.EVENT_BUS.register(new WellDecorateEvent());
        MinecraftForge.EVENT_BUS.register(new ConfigReloader());
    }

    public void postInit(FMLPostInitializationEvent e) {}

    public void registerRender() {}

    public void registerEvents() {

    }

}
