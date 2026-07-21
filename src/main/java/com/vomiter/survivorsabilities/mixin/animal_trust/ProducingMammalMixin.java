package com.vomiter.survivorsabilities.mixin.animal_trust;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.vomiter.survivorsabilities.util.AnimalTrustGetter;
import com.vomiter.survivorsabilities.util.SAThreadLocals;
import net.dries007.tfc.common.entities.livestock.DairyAnimal;
import net.dries007.tfc.common.entities.livestock.ProducingMammal;
import net.dries007.tfc.common.entities.livestock.WoolyAnimal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
