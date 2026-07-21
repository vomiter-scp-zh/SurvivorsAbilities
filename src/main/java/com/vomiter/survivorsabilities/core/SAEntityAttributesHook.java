package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import net.dries007.tfc.common.entities.TFCEntities;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = SurvivorsAbilities.MODID)
public final class SAEntityAttributesHook {
    private SAEntityAttributesHook() {}

    @SubscribeEvent
    public static void onEntityAttributeModify(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SAAttributes.MAX_LOAD);
        event.add(TFCEntities.HORSE.get(), SAAttributes.MAX_LOAD);
        event.add(TFCEntities.DONKEY.get(), SAAttributes.MAX_LOAD);
        event.add(TFCEntities.MULE.get(), SAAttributes.MAX_LOAD);

        event.add(EntityType.PLAYER, SAAttributes.HUNGER_TOLERANCE);
        event.add(EntityType.PLAYER, SAAttributes.APPETITE);
        event.add(EntityType.PLAYER, SAAttributes.RESILIENCE);

        event.add(EntityType.PLAYER, SAAttributes.TFC_FORTUNE);
        event.add(EntityType.PLAYER, SAAttributes.BLOOD_SCENT);
        event.add(EntityType.PLAYER, SAAttributes.ANIMAL_TRUST);
    }
}
