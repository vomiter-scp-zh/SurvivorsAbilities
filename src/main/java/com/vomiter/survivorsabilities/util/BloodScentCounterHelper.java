package com.vomiter.survivorsabilities.util;

import com.vomiter.survivorsabilities.core.ModCapabilities;
import com.vomiter.survivorsabilities.core.world.IChunkCounter;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.NonNullConsumer;

import java.util.Optional;

public final class BloodScentCounterHelper {

    public static Optional<IChunkCounter> get(LevelChunk chunk) {
        return chunk.getCapability(ModCapabilities.BLOOD_SCENT_COUNTER)
                .resolve();
    }

    public static void ifPresent(
            LevelChunk chunk,
            NonNullConsumer<IChunkCounter> action
    ) {
        chunk.getCapability(ModCapabilities.BLOOD_SCENT_COUNTER)
                .ifPresent(action);
    }

    public static int getValue(LevelChunk chunk) {
        return chunk.getCapability(ModCapabilities.BLOOD_SCENT_COUNTER)
                .map(IChunkCounter::get)
                .orElse(0);
    }
}