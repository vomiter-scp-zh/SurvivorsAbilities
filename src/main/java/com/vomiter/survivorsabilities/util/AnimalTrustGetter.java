package com.vomiter.survivorsabilities.util;

import com.vomiter.survivorsabilities.core.SAAttributes;
import net.minecraft.world.entity.player.Player;

public class AnimalTrustGetter {
    public static double getAnimalTrust(Player player){
        return player.getAttribute(SAAttributes.ANIMAL_TRUST.get()).getValue() / 100d;
    }

    public static double getAnimalTrust(){
        return SAThreadLocals.cachedPlayer.get() == null ? 0: getAnimalTrust(SAThreadLocals.cachedPlayer.get());
    }

}
