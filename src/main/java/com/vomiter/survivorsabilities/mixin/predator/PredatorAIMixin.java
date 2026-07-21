package com.vomiter.survivorsabilities.mixin.predator;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.vomiter.survivorsabilities.core.SAEffects;
import net.dries007.tfc.common.entities.ai.TFCBrain;
import net.dries007.tfc.common.entities.ai.predator.PredatorAi;
import net.dries007.tfc.common.entities.livestock.TFCAnimal;
import net.dries007.tfc.common.entities.predator.Predator;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PredatorAi.class)
public abstract class PredatorAIMixin {

    @WrapOperation(
            method = "updateActivity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/Brain;" +
                            "updateActivityFromSchedule(JJ)V"
            )
    )
    private static void sa$forceHuntWhileAffected(
            Brain<?> brain,
            long dayTime,
            long gameTime,
            Operation<Void> original,
            @Local(argsOnly = true) Predator predator
    ) {
        if (predator.hasEffect(SAEffects.AGITATION)) {
            predator.setSleeping(false);

            if (!brain.isActive(Activity.FIGHT)) {
                brain.setActiveActivityIfPossible(TFCBrain.HUNT.get());
            }
            return;
        }

        original.call(brain, dayTime, gameTime);
    }
}