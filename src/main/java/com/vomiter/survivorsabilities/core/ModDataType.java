package com.vomiter.survivorsabilities.core;

import com.mojang.serialization.Codec;
import com.vomiter.survivorsabilities.SurvivorsAbilities;
import com.vomiter.survivorsabilities.core.world.BloodScentCounter;
import com.vomiter.survivorsabilities.core.world.IChunkCounter;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class ModDataType {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SurvivorsAbilities.MODID);

    public static final Supplier<AttachmentType<BloodScentCounter>> BLOOD_SCENT_COUNTER =
            ATTACHMENT_TYPES.register(
                    "blood_scent", () -> AttachmentType.builder(() -> new BloodScentCounter(0))
                            .serialize(Codec.INT.xmap(BloodScentCounter::new, BloodScentCounter::get))
                            .build()
            );

    private ModDataType() {
    }
}