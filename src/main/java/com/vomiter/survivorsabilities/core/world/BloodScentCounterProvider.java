package com.vomiter.survivorsabilities.core.world;

import com.vomiter.survivorsabilities.core.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BloodScentCounterProvider implements ICapabilitySerializable<CompoundTag> {

    private final BloodScentCounter counter = new BloodScentCounter();
    private final LazyOptional<IChunkCounter> optional =
            LazyOptional.of(() -> counter);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(
            @NotNull Capability<T> capability,
            @Nullable Direction side
    ) {
        if (capability == ModCapabilities.BLOOD_SCENT_COUNTER) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return counter.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        counter.deserializeNBT(tag);
    }

    public void invalidate() {
        optional.invalidate();
    }
}