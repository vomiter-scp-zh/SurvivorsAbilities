package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import net.dries007.tfc.common.entities.TFCEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SurvivorsAbilities.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SAEntityAttributesHook {
    private SAEntityAttributesHook() {}

    @SubscribeEvent
    public static void onEntityAttributeModify(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SAAttributes.MAX_LOAD.get());
        event.add(TFCEntities.HORSE.get(), SAAttributes.MAX_LOAD.get());
        event.add(TFCEntities.DONKEY.get(), SAAttributes.MAX_LOAD.get());
        event.add(TFCEntities.MULE.get(), SAAttributes.MAX_LOAD.get());

        event.add(EntityType.PLAYER, SAAttributes.HUNGER_TOLERANCE.get());
        event.add(EntityType.PLAYER, SAAttributes.RESILIENCE.get());
        event.add(EntityType.PLAYER, SAAttributes.APPETITE.get());
        event.add(EntityType.PLAYER, SAAttributes.TFC_FORTUNE.get());
        event.add(EntityType.PLAYER, SAAttributes.BLOOD_SCENT.get());
        event.add(EntityType.PLAYER, SAAttributes.ANIMAL_TRUST.get());
    }
}
