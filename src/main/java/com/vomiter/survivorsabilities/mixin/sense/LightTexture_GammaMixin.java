package com.vomiter.survivorsabilities.mixin.sense;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.vomiter.survivorsabilities.core.SAEffects;
import com.vomiter.survivorsabilities.core.effect.SenseEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightTexture.class)
public abstract class LightTexture_GammaMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyExpressionValue(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z", ordinal = 0))
    private boolean turnOnNightVision(boolean b){
        if(b) return true;
        LocalPlayer player = minecraft.player;
        if(player == null) return false;
        if(player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.PLUTO)) && player.level().dimensionType().ultraWarm()) return true;
        if(player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.ARTEMIS))) return true;
        return player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.NEPTUNE)) && player.isEyeInFluid(FluidTags.WATER);
    }

    @WrapOperation(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private float setNightVisionScale(LivingEntity player, float p_109110_, Operation<Float> original){
        if(player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.NEPTUNE)) && player.isEyeInFluid(FluidTags.WATER)) return 1;
        if(player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.PLUTO)) && player.level().dimensionType().ultraWarm()) return 1;
        if(player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.ARTEMIS)) && !player.level().dimensionType().hasFixedTime() && player.level().dayTime() % 24000 >= 13000) return 1;

        if(!player.hasEffect(MobEffects.NIGHT_VISION)) return 0f;
        return original.call(player, p_109110_);
    }

}
