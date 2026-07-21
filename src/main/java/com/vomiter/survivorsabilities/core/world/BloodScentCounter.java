package com.vomiter.survivorsabilities.core.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraftforge.common.util.INBTSerializable;

public class BloodScentCounter implements IChunkCounter, INBTSerializable<CompoundTag> {

    private static final String NBT_VALUE = "BloodScent";

    private int value;

    @Override
    public int get() {
        return value;
    }

    @Override
    public void add(int amount) {
        if (amount <= 0) {
            return;
        }

        value = Math.min(value + amount, MAX_VALUE);
    }

    @Override
    public void reduce(int amount) {
        if (amount <= 0) {
            return;
        }

        value = Math.max(value - amount, 0);
    }

    @Override
    public void clear() {
        value = 0;
    }

    @Override
    public boolean isMax() {
        return value >= MAX_VALUE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(NBT_VALUE, value);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        value = Mth.clamp(0, tag.getInt(NBT_VALUE), MAX_VALUE);
    }
}