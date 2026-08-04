package com.vomiter.survivorsabilities.mixin.sense;

import com.llamalad7.mixinextras.sugar.Local;
import com.vomiter.survivorsabilities.core.SAEffects;
import com.vomiter.survivorsabilities.core.effect.SenseEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @ModifyArg(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V"
            )
    )
    private static float modifyFogEnd(float original, @Local FogType type) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return original;
        boolean isNeptune = player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.NEPTUNE));
        boolean isPluto = player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.PLUTO));
        if(isNeptune && type.equals(FogType.WATER)) return original * 5.0F;
        if(isPluto && player.level().dimension().equals(ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("minecraft", "the_nether")))) return original * 5.0f;
        return original;
    }

    @ModifyArg(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"
            )
    )
    private static float modifyFogStart(float original, @Local FogType type) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return original;
        boolean isNeptune = player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.NEPTUNE));
        boolean isPluto = player.hasEffect(SAEffects.SENSES.get(SenseEffect.SenseType.PLUTO));
        if(isNeptune && type.equals(FogType.WATER)) return original * 5.0F;
        if(isPluto && player.level().dimension().equals(ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("minecraft", "the_nether")))) return original * 5.0f;
        return original;
    }

}
