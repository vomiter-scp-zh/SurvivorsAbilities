package com.vomiter.survivorsabilities;

import com.mojang.logging.LogUtils;
import com.vomiter.survivorsabilities.client.ClientEventHandler;
import com.vomiter.survivorsabilities.core.ForgeEventHandler;
import com.vomiter.survivorsabilities.core.SAAttributes;
import com.vomiter.survivorsabilities.core.SAEffects;
import com.vomiter.survivorsabilities.core.world.ChunkCapabilityEvents;
import com.vomiter.survivorsabilities.data.SAEntityTypeTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(SurvivorsAbilities.MODID)
public class SurvivorsAbilities
{
    public static final String MODID = "survivorsabilities";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static FMLJavaModLoadingContext context;
    private static IEventBus modBus;

    public SurvivorsAbilities(FMLJavaModLoadingContext context)
    {
        SurvivorsAbilities.context = context;
        modBus = context.getModEventBus();
        common();
    }

    public SurvivorsAbilities()
    {
        context = FMLJavaModLoadingContext.get();
        modBus = context.getModEventBus();
        common();
    }

    private void common(){
        context.registerConfig(ModConfig.Type.COMMON, SAConfig.COMMON_SPEC);
        SAAttributes.ATTRIBUTES.register(modBus);
        SAEffects.EFFECTS.register(modBus);

        modBus.addListener(SAEntityTypeTags::onGatherData);
        modBus.addListener(ChunkCapabilityEvents::registerCapabilities);
        ForgeEventHandler.init();

        if (FMLEnvironment.dist == Dist.CLIENT){
            ClientEventHandler.init();
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

}
