package com.vomiter.survivorsabilities.util;

import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class SAThreadLocals {
    public static ThreadLocal<Player> cachedPlayer = ThreadLocal.withInitial(() -> null);
}
