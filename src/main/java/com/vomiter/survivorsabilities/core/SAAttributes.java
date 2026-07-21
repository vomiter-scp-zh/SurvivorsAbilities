package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SAAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, SurvivorsAbilities.MODID);

    public static final DeferredHolder<Attribute, Attribute> ANIMAL_TRUST = ATTRIBUTES.register(
            "animal_trust",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".animal_trust",
                    0.0D, -100.0D, 100.0D
            ).setSyncable(true)
    );


    public static final DeferredHolder<Attribute, Attribute> BLOOD_SCENT = ATTRIBUTES.register(
            "blood_scent",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".blood_scent",
                    0.0D, 0.0D, 10.0D
            ).setSyncable(true)
    );


    public static final DeferredHolder<Attribute, Attribute> TFC_FORTUNE = ATTRIBUTES.register(
            "tfc_fortune",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".tfc_fortune",
                    0.0D, 0.0D, 10.0D
            ).setSyncable(true)
    );


    public static final DeferredHolder<Attribute, Attribute> MAX_LOAD = ATTRIBUTES.register(
            "max_load",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".max_load",
                    1.0D, 1.0D, 31.0D
            ).setSyncable(true)
    );

    public static final DeferredHolder<Attribute, Attribute> HUNGER_TOLERANCE = ATTRIBUTES.register(
            "hunger_tolerance",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".hunger_tolerance",
                    0.0D, 0.0D, 10.0D
            ).setSyncable(true)
    );

    public static final DeferredHolder<Attribute, Attribute> APPETITE = ATTRIBUTES.register(
            "appetite",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".appetite",
                    0.0D, 0.0D, 20.0D
            ).setSyncable(true)
    );

    public static final DeferredHolder<Attribute, Attribute> RESILIENCE = ATTRIBUTES.register(
            "resilience",
            () -> new RangedAttribute(
                    "attribute.name." + SurvivorsAbilities.MODID + ".resilience",
                    0D, 0D, 3D
            ).setSyncable(true)
    );



}
