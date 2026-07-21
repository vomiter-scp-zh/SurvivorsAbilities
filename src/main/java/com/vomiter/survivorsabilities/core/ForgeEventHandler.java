package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SAConfig;
import com.vomiter.survivorsabilities.SAHelper;
import com.vomiter.survivorsabilities.core.world.BloodScentEvents;
import net.dries007.tfc.common.effect.TFCEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.Objects;

public class ForgeEventHandler {
    public static void init(){
        final IEventBus bus = NeoForge.EVENT_BUS;
        bus.addListener(ForgeEventHandler::onMobEffectApplicable);
        bus.addListener(BloodScentEvents::onLivingTick);
    }

    public static void onMobEffectApplicable(MobEffectEvent.Applicable event){
        boolean forceCancel = SAConfig.COMMON.forceCancelOverburden.get();
        if(!forceCancel) return;
        if(!event.getEffectInstance().getEffect().equals(TFCEffects.OVERBURDENED.get())) return;
        if(!(event.getEntity() instanceof Player player)) return;
        double max_load = Objects.requireNonNull(player.getAttribute(SAAttributes.MAX_LOAD)).getValue();
        boolean shouldCancel = SAHelper.countHeavy(player) <= max_load;
        if(shouldCancel) event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }
}
