package com.vomiter.survivorsabilities.mixin.nourishment;

import com.llamalad7.mixinextras.sugar.Local;
import com.vomiter.survivorsabilities.core.SAAttributes;
import net.dries007.tfc.common.player.PlayerInfo;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static net.dries007.tfc.common.player.PlayerInfo.PASSIVE_HEALING_PER_TEN_TICKS;

@Mixin(PlayerInfo.class)
public abstract class TFCFoodData_Mixin {

    @Shadow @Final private Player player;

    @Shadow public abstract int getFoodLevel();

    @Shadow public abstract float getThirst();

    @ModifyConstant(method = "addExhaustion", constant = @Constant(floatValue = 0.4f))
    private float tolerateExhaustion(float value, @Local(argsOnly = true) float toAdd){
        if (toAdd <= 0) return value;

        boolean shouldAllowExhaustion =
                player.isHurt()
                        && getFoodLevel() >= 4
                        && getThirst() > 20;

        if (shouldAllowExhaustion) {
            return value;
        }

        float toleranceLvl = (float) Objects.requireNonNull(
                player.getAttribute(SAAttributes.HUNGER_TOLERANCE)
        ).getValue();

        float factor = 1f - (toleranceLvl / 10f);   // tolerance=10 -> factor=0
        return Math.max(value * factor, 0f);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void healForExtreme(Player player, CallbackInfo ci){
        if(getFoodLevel() < 4 || getThirst() <= 20){
            if(player.tickCount % 10 == 0 && player.isHurt()) player.heal(
                    (float) (player.getAttribute(SAAttributes.RESILIENCE).getValue()
                            * PASSIVE_HEALING_PER_TEN_TICKS
                            * TFCConfig.SERVER.naturalRegenerationModifier.get().floatValue())
            );
        }
    }


    @ModifyArg(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"),
            index = 0,
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isHurt()Z")
            )
    )
    private float buffNaturalHeal(float original) {
        float resilienceHeal = (float) (Objects.requireNonNull(player.getAttribute(SAAttributes.RESILIENCE)).getValue() * 0.0039999997F * TFCConfig.SERVER.naturalRegenerationModifier.get().floatValue());
        return Math.max(resilienceHeal, original);
    }
}
