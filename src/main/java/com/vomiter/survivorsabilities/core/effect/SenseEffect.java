package com.vomiter.survivorsabilities.core.effect;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;

import java.util.Locale;

public class SenseEffect extends MobEffect {
    public enum SenseType{
        NEPTUNE,
        PLUTO,
        ARTEMIS
    }

    public enum GlowColor {
        WHITE,
        YELLOW,
        RED,
        NONE
    }

    public static TagKey<EntityType<?>> getTargetTag(SenseType s, GlowColor c){
        String path =
                (c.equals(GlowColor.NONE)? "": (c.name().toLowerCase(Locale.ROOT) + "_"))
                + "highlight_in_"
                + s.name().toLowerCase(Locale.ROOT) + "_vision";
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(SurvivorsAbilities.MODID, path));
    }

    public SenseEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }
}
