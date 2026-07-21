package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SAConfig;
import com.vomiter.survivorsabilities.SAHelper;
import com.vomiter.survivorsabilities.core.world.BloodScentEvents;
import com.vomiter.survivorsabilities.core.world.ChunkCapabilityEvents;
import net.dries007.tfc.common.TFCEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Objects;

public class ForgeEventHandler {
    public static void init(){
        final IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(ForgeEventHandler::onMobEffectApplicable);
        bus.addGenericListener(LevelChunk.class, ChunkCapabilityEvents::attachChunkCapability);
        bus.addListener(BloodScentEvents::onLivingTick);
    }

    public static void onMobEffectApplicable(MobEffectEvent.Applicable event){
        boolean forceCancel = SAConfig.COMMON.forceCancelOverburden.get();
        if(!forceCancel) return;
        if(!event.getEffectInstance().getEffect().equals(TFCEffects.OVERBURDENED.get())) return;
        if(!(event.getEntity() instanceof Player player)) return;
        double max_load = Objects.requireNonNull(player.getAttribute(SAAttributes.MAX_LOAD.get())).getValue();
        boolean shouldCancel = SAHelper.countHeavy(player) <= max_load;
        if(shouldCancel) event.setResult(Event.Result.DENY);

    }
}
