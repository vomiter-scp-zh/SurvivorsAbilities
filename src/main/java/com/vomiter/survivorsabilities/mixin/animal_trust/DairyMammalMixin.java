package com.vomiter.survivorsabilities.mixin.animal_trust;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.vomiter.survivorsabilities.util.AnimalTrustGetter;
import com.vomiter.survivorsabilities.util.SAThreadLocals;
import net.dries007.tfc.common.entities.livestock.DairyAnimal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DairyAnimal.class, remap = false)
public class DairyMammalMixin {
    @WrapMethod(method = "mobInteract")
    private InteractionResult sa$mobInteract(Player player, InteractionHand hand, Operation<InteractionResult> original){
        try{
            SAThreadLocals.cachedPlayer.set(player);
            return original.call(player, hand);
        } finally {
            SAThreadLocals.cachedPlayer.remove();
        }
    }

    @WrapOperation(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/dries007/tfc/common/entities/livestock/DairyAnimal;getFamiliarity()F"))
    private float sa$getFamiliarity(DairyAnimal instance, Operation<Float> original){
        var originalResult = original.call(instance);
        return (float) (originalResult + AnimalTrustGetter.getAnimalTrust());
    }
}
