package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import com.vomiter.survivorsabilities.core.effect.AppetiteEffect;
import com.vomiter.survivorsabilities.core.effect.OvereatenEffect;
import com.vomiter.survivorsabilities.core.effect.SenseEffect;
import com.vomiter.survivorsabilities.core.effect.WorkHorseEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class SAEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, SurvivorsAbilities.MODID);

    public static final Map<SenseEffect.SenseType, DeferredHolder<MobEffect, MobEffect>> SENSES = new EnumMap<>(SenseEffect.SenseType.class);
    static {
        for (SenseEffect.SenseType senseType : SenseEffect.SenseType.values()) {
            SENSES.put(senseType, EFFECTS.register(senseType.name().toLowerCase(Locale.ROOT) + "_vision", SenseEffect::new));
        }
    }
    public static final DeferredHolder<MobEffect, WorkHorseEffect> WORKHORSE = EFFECTS.register("workhorse", WorkHorseEffect::new);
    public static final DeferredHolder<MobEffect, OvereatenEffect> OVEREATEN = EFFECTS.register("overeaten", OvereatenEffect::new);
    public static final DeferredHolder<MobEffect, AppetiteEffect> APPETITE = EFFECTS.register("appetite", AppetiteEffect::new);


}
