package astrotibs.villagenames.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.guardian.audio.GuardianSound;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.renderer.RenderGuardian;
import astrotibs.villagenames.prismarine.render.RegisterRenderPrismarine;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public static int renderGlazedTerracotta;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void registerRender() {
        if (GeneralConfig.addOceanMonuments) {
            RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
        }
    }

    @Override
    public void registerEvents() {
        super.registerEvents();
        if (GeneralConfig.addOceanMonuments) {
            FMLCommonHandler.instance()
                .bus()
                .register(RegisterRenderPrismarine.INSTANCE);
            MinecraftForge.EVENT_BUS.register(RegisterRenderPrismarine.INSTANCE);
            // RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
        }
    }

    public static void handleHealthUpdate(EntityGuardian guardian) {
        Minecraft.getMinecraft()
            .getSoundHandler()
            .playSound(new GuardianSound(guardian));
    }
    /*
     * public static void playEndPortalSound() {
     * //Minecraft.getMinecraft().getSoundHandler().playSound(new EndPortalActivateSound() );
     * Minecraft.getMinecraft().getSoundHandler().playSound(
     * PositionedSoundRecord.func_147673_a(new ResourceLocation("VillageNames:block.end_portal.endportal"))
     * );
     * }
     */
}
