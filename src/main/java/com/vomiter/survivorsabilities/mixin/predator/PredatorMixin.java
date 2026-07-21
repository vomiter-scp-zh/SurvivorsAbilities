package com.vomiter.survivorsabilities.mixin.predator;

import com.vomiter.survivorsabilities.core.SAEffects;
import net.dries007.tfc.common.entities.ai.TFCBrain;
import net.dries007.tfc.common.entities.predator.Predator;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Predator.class)
public abstract class PredatorMixin {

    @Shadow
    public abstract void setSleeping(boolean asleep);

    @Inject(
        method = "customServerAiStep",
        at = @At("TAIL")
    )
    private void sa$forceHuntWhileAffected(CallbackInfo ci) {
        Predator predator = (Predator) (Object) this;

        if (!predator.hasEffect(SAEffects.AGITATION.get())) {
            return;
        }

        Brain<Predator> brain = predator.getBrain();

        setSleeping(false);

        if (!brain.isActive(Activity.FIGHT)
            && !brain.isActive(Activity.AVOID)) {
            brain.setActiveActivityIfPossible(TFCBrain.HUNT.get());
        }
    }
}