package com.vomiter.survivorsabilities.util;

import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.predator.Predator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;

import java.util.*;

public class TFCPredatorHelper {

    private static final List<EntityType<? extends Mob>> TFC_PREDATORS = List.of(
            TFCEntities.POLAR_BEAR.get(),
            TFCEntities.GRIZZLY_BEAR.get(),
            TFCEntities.BLACK_BEAR.get(),
            TFCEntities.COUGAR.get(),
            TFCEntities.PANTHER.get(),
            TFCEntities.LION.get(),
            TFCEntities.SABERTOOTH.get(),
            TFCEntities.TIGER.get(),
            TFCEntities.CROCODILE.get(),
            TFCEntities.WOLF.get(),
            TFCEntities.HYENA.get(),
            TFCEntities.DIREWOLF.get()
    );

    public static Optional<EntityType<? extends Mob>> selectLocalPredator(
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        List<EntityType<? extends Mob>> candidates =
                new ArrayList<>(TFC_PREDATORS);

        Collections.shuffle(candidates, new Random(random.nextLong()));

        for (EntityType<? extends Mob> type : candidates) {
            if (SpawnPlacements.checkSpawnRules(
                    type,
                    level,
                    MobSpawnType.EVENT,
                    pos,
                    random
            )) {
                return Optional.of(type);
            }
        }

        return Optional.empty();
    }
}
