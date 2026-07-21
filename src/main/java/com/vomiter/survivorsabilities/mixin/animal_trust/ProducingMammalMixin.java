package com.vomiter.survivorsabilities.mixin.animal_trust;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.vomiter.survivorsabilities.util.AnimalTrustGetter;
import net.dries007.tfc.common.entities.livestock.ProducingMammal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ProducingMammal.class, remap = false)
public class ProducingMammalMixin {
    @WrapOperation(method = "isReadyForAnimalProduct", at = @At(value = "INVOKE", target = "Lnet/dries007/tfc/common/entities/livestock/ProducingMammal;getFamiliarity()F"))
    private float sa$getFamiliarity(ProducingMammal instance, Operation<Float> original){
        var originalResult = original.call(instance);
        return (float) (originalResult + AnimalTrustGetter.getAnimalTrust());
    }
}
