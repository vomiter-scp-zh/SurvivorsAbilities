package com.vomiter.survivorsabilities.mixin.sense;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.vomiter.survivorsabilities.core.SAEffects;
import com.vomiter.survivorsabilities.core.effect.SenseEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public abstract class LevelRenderer_SenseGlowMixin {
    @WrapOperation(
            method = "renderLevel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z")
    )
    private boolean forceGlowing(Minecraft instance, Entity entity, Operation<Boolean> original){
        LocalPlayer viewer = instance.player;
        for (SenseEffect.SenseType senseType : SenseEffect.SenseType.values()) {
            if (viewer != null
                    && viewer.hasEffect(SAEffects.SENSES.get(senseType))
                    && entity instanceof LivingEntity le
                    && le.getType().is(SenseEffect.getTargetTag(senseType, SenseEffect.GlowColor.NONE))
                    && le.distanceTo(viewer) < 32
            ) return true;
        }
        return original.call(instance, entity);
    }


    @WrapOperation(
            method =
                    "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/OutlineBufferSource;setColor(IIII)V"
            )
    )
    private void colorOutline(
            OutlineBufferSource instance, int red, int green, int blue, int alpha, Operation<Void> original, @Local Entity entity
    ) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer viewer = mc.player;
        if(viewer == null) return;
        if(!(entity instanceof LivingEntity le)) return;
        if(!le.isAlive()) return;

        // 預設顏色 = 原本的 r/g/b/a
        int cr = red;
        int cg = green;
        int cb = blue;
        int ca = alpha;

        for (SenseEffect.SenseType senseType : SenseEffect.SenseType.values()) {
            if(!viewer.hasEffect(SAEffects.SENSES.get(senseType))) continue;
            for (SenseEffect.GlowColor glowColor : SenseEffect.GlowColor.values()) {
                if(glowColor.equals(SenseEffect.GlowColor.NONE)) continue;
                if(!le.getType().is(SenseEffect.getTargetTag(senseType, glowColor))) continue;
                if(glowColor.equals(SenseEffect.GlowColor.RED)) {
                    cr = 255;
                    cg = 0;
                    cb = 0;
                }
                else if(glowColor.equals(SenseEffect.GlowColor.YELLOW)) {
                    cr = 255;
                    cg = 255;
                    cb = 0;
                }
            }
        }
        original.call(instance, cr, cg, cb, ca);
    }
}
