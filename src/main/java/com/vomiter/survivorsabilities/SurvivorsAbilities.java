package com.vomiter.survivorsabilities;

import com.mojang.logging.LogUtils;
import com.vomiter.survivorsabilities.client.ClientEventHandler;
import com.vomiter.survivorsabilities.core.ForgeEventHandler;
import com.vomiter.survivorsabilities.core.ModDataType;
import com.vomiter.survivorsabilities.core.SAAttributes;
import com.vomiter.survivorsabilities.core.SAEffects;
import com.vomiter.survivorsabilities.data.SAEntityTypeTags;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(SurvivorsAbilities.MODID)
public class SurvivorsAbilities
{
    public static final String MODID = "survivorsabilities";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static IEventBus modBus;

    public SurvivorsAbilities(ModContainer mod, IEventBus bus)
    {
        modBus = bus;
        mod.registerConfig(ModConfig.Type.COMMON, SAConfig.COMMON_SPEC);
        SAAttributes.ATTRIBUTES.register(modBus);
        SAEffects.EFFECTS.register(modBus);
        ModDataType.ATTACHMENT_TYPES.register(modBus);

        modBus.addListener(SAEntityTypeTags::onGatherData);
        ForgeEventHandler.init();

        if (FMLEnvironment.dist == Dist.CLIENT){
            ClientEventHandler.init();
        }
    }

}
