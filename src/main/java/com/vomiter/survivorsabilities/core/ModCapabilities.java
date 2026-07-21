package com.vomiter.survivorsabilities.core;

import com.vomiter.survivorsabilities.core.world.IChunkCounter;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class ModCapabilities {

    public static final Capability<IChunkCounter> BLOOD_SCENT_COUNTER =
            CapabilityManager.get(new CapabilityToken<>() {});

    private ModCapabilities() {
    }
}