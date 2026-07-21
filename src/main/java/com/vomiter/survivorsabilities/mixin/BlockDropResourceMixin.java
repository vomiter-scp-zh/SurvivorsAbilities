package com.vomiter.survivorsabilities.mixin;

import com.vomiter.survivorsabilities.core.SAAttributes;
import com.vomiter.survivorsabilities.util.TFCFortuneMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BlockDropResourceMixin {
    @Inject(
            method = "getDrops(" +
                    "Lnet/minecraft/world/level/block/state/BlockState;" +
                    "Lnet/minecraft/server/level/ServerLevel;" +
                    "Lnet/minecraft/core/BlockPos;" +
                    "Lnet/minecraft/world/level/block/entity/BlockEntity;" +
                    "Lnet/minecraft/world/entity/Entity;" +
                    "Lnet/minecraft/world/item/ItemStack;)" +
                    "Ljava/util/List;",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void sa$getDrops(
            BlockState state,
            ServerLevel serverLevel,
            BlockPos pos,
            BlockEntity blockEntity,
            Entity entity,
            ItemStack stack,
            CallbackInfoReturnable<List<ItemStack>> cir){
        if(entity instanceof ServerPlayer serverPlayer){
            var tfcFortune = Optional.ofNullable(serverPlayer.getAttribute(SAAttributes.TFC_FORTUNE))
                    .map(AttributeInstance::getValue)
                    .orElse(0D);
            if(tfcFortune == 0) return;
            var newList = new ArrayList<ItemStack>();
            for (ItemStack lootStack : cir.getReturnValue()) {
                if(serverLevel.random.nextFloat() < tfcFortune * 0.1){
                    newList.addAll(TFCFortuneMap.getFortuneResult(lootStack.getItem()));
                }
                else newList.add(lootStack);
            }
            cir.setReturnValue(newList);
        }
    }
}
