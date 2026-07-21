package com.vomiter.survivorsabilities.core.world;

public interface IChunkCounter {

    int MAX_VALUE = 256;

    int get();

    void add(int amount);

    void reduce(int amount);

    void clear();

    boolean isMax();
}