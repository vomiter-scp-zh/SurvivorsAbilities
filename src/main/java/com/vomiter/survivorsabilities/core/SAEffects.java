package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import com.vomiter.survivorsabilities.core.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class SAEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SurvivorsAbilities.MODID);
    public static final RegistryObject<MobEffect> WORKHORSE = EFFECTS.register("workhorse", WorkHorseEffect::new);
    public static final RegistryObject<MobEffect> OVEREATEN = EFFECTS.register("overeaten", OvereatenEffect::new);
    public static final RegistryObject<MobEffect> APPETITE = EFFECTS.register("appetite", AppetiteEffect::new);
    public static final Map<SenseEffect.SenseType, RegistryObject<MobEffect>> SENSES = new EnumMap<>(SenseEffect.SenseType.class);
    public static final RegistryObject<MobEffect> AGITATION = EFFECTS.register("agitation", () -> new GenericEffect(MobEffectCategory.NEUTRAL, 0));
    static {
        for (SenseEffect.SenseType senseType : SenseEffect.SenseType.values()) {
            SENSES.put(senseType, EFFECTS.register(senseType.name().toLowerCase(Locale.ROOT) + "_vision", SenseEffect::new));
        }
    }
}
