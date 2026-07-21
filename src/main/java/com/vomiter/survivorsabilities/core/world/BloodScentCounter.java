package com.vomiter.survivorsabilities.core.world;

public class BloodScentCounter implements IChunkCounter {

    public static final String NBT_VALUE = "BloodScent";

    private int value;

    public BloodScentCounter(int value){
        this.value = value;
    }

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
}