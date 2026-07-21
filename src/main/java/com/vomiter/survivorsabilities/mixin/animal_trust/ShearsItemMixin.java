package com.vomiter.survivorsabilities.mixin.animal_trust;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.vomiter.survivorsabilities.util.SAThreadLocals;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {
    @WrapMethod(method = "interactLivingEntity", remap = false)
    private InteractionResult sa$interactEntity(ItemStack stack, Player playerIn, LivingEntity entity, InteractionHand hand, Operation<InteractionResult> original){
        try {
            SAThreadLocals.cachedPlayer.set(playerIn);
            return original.call(stack, playerIn, entity, hand);
        }finally {
            SAThreadLocals.cachedPlayer.remove();
        }
    }
}
