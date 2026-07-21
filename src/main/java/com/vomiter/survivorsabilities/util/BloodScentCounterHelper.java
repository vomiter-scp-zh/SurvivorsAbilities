package com.vomiter.survivorsabilities.util;

import com.vomiter.survivorsabilities.core.ModDataType;
import com.vomiter.survivorsabilities.core.world.BloodScentCounter;
import com.vomiter.survivorsabilities.core.world.IChunkCounter;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Optional;
import java.util.function.Consumer;

public final class BloodScentCounterHelper {

    public static Optional<BloodScentCounter> get(LevelChunk chunk) {

        return chunk.getExistingData(ModDataType.BLOOD_SCENT_COUNTER);
    }

    public static void ifPresent(
            LevelChunk chunk,
            Consumer<IChunkCounter> action
    ) {
        if (!chunk.hasData(ModDataType.BLOOD_SCENT_COUNTER)){
            chunk.setData(ModDataType.BLOOD_SCENT_COUNTER, new BloodScentCounter(0));
        }
        chunk.getExistingData(ModDataType.BLOOD_SCENT_COUNTER).ifPresent(action);
        chunk.setUnsaved(true);
    }

    public static int getValue(LevelChunk chunk) {
        return chunk.getExistingData(ModDataType.BLOOD_SCENT_COUNTER)
                .map(IChunkCounter::get)
                .orElse(0);
    }
}