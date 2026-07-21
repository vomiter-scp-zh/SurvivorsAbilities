package com.vomiter.survivorsabilities.util;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TFCFortuneMap {
    public static HashMap<Item, List<ItemStack>> entries = new HashMap<>();

    public static List<ItemStack> getFortuneResult(Item item){
        return entries.computeIfAbsent(
                item,
                item1 -> {
                    var id = BuiltInRegistries.ITEM.getKey(item1);
                    if(id.getPath().startsWith("ore/")){
                        for (int i = 0; i < Ore.Grade.values().length; i++) {
                            var gradePrefix = Ore.Grade.values()[i].name().toLowerCase(Locale.ROOT) + "_";
                            if(id.getPath().contains(gradePrefix)){
                                if(i < Ore.Grade.values().length - 1){
                                    var upgraded = BuiltInRegistries.ITEM.get(
                                            ResourceLocation.fromNamespaceAndPath(
                                                    id.getNamespace(),
                                                    id.getPath().replace(gradePrefix, Ore.Grade.values()[i].name().toLowerCase(Locale.ROOT) + "_")));
                                    if(upgraded != null && !upgraded.getDefaultInstance().isEmpty()) return List.of(upgraded.getDefaultInstance());
                                }
                                var poor = BuiltInRegistries.ITEM.get(
                                        ResourceLocation.fromNamespaceAndPath(
                                                id.getNamespace(),
                                                id.getPath().replace(gradePrefix, Ore.Grade.values()[i].name().toLowerCase(Locale.ROOT) + "_")));
                                if(poor != null && !poor.getDefaultInstance().isEmpty()) return List.of(item.getDefaultInstance(), poor.getDefaultInstance());
                            }
                        }
                        return List.of(item.getDefaultInstance().copyWithCount(2));
                    }
                    return List.of();
                }
        );
    }
}
