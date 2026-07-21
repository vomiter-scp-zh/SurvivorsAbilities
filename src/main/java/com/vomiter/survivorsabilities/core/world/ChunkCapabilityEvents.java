package com.vomiter.survivorsabilities.core.world;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public final class ChunkCapabilityEvents {

    private static final ResourceLocation CHUNK_COUNTER_ID =
            ResourceLocation.fromNamespaceAndPath(SurvivorsAbilities.MODID, "chunk_counter");

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IChunkCounter.class);
    }

    public static void attachChunkCapability(
            AttachCapabilitiesEvent<LevelChunk> event
    ) {
        BloodScentCounterProvider provider = new BloodScentCounterProvider();

        event.addCapability(CHUNK_COUNTER_ID, provider);
        event.addListener(provider::invalidate);
    }
}