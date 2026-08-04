package com.vomiter.survivorsabilities.data;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import com.vomiter.survivorsabilities.core.effect.SenseEffect;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.entities.TFCEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class SAEntityTypeTags extends EntityTypeTagsProvider {
    public enum Tags{
        Tags(){};

        public TagKey<EntityType<?>> get(){
            return SAEntityTypeTags.create(this);
        }
    }

    public static TagKey<EntityType<?>> create(String path){
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("survivorsabilities", path));
    }
    public static TagKey<EntityType<?>> create(Tags path){
        return create(path.name().toLowerCase(Locale.ROOT));
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider p_255894_) {
        tag(SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.WHITE))
                .add(TFCEntities.FRESHWATER_FISH.values().stream().map(Supplier::get).toArray(EntityType<?>[]::new))
                .add(TFCEntities.TROPICAL_FISH.get(), TFCEntities.COD.get(), TFCEntities.OCTOPOTEUTHIS.get(), TFCEntities.SQUID.get());
        tag(SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.YELLOW))
                .addOptionalTag(TFCTags.Entities.NEEDS_LARGE_FISHING_BAIT);
        tag(SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.RED))
                .add(TFCEntities.CROCODILE.get());
        tag(SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.NONE)).addTags(
                SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.WHITE),
                SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.YELLOW),
                SenseEffect.getTargetTag(SenseEffect.SenseType.NEPTUNE, SenseEffect.GlowColor.RED)
        );
    }

    public SAEntityTypeTags(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, modId, existingFileHelper);
    }

    public static void onGatherData(GatherDataEvent event){
        var self = new SAEntityTypeTags(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider(),
                SurvivorsAbilities.MODID,
                event.getExistingFileHelper()
        );
        event.getGenerator().addProvider(event.includeServer(), self);
    }
}
