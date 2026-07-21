package com.vomiter.survivorsabilities.core.world;

import com.vomiter.survivorsabilities.SurvivorsAbilities;
import com.vomiter.survivorsabilities.core.SAAttributes;
import com.vomiter.survivorsabilities.util.BloodScentCounterHelper;
import com.vomiter.survivorsabilities.util.TFCPredatorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashSet;
import java.util.Set;

public final class BloodScentEvents {

    private static final int UPDATE_INTERVAL = 20 * 30;

    private static final int SPREAD_RADIUS = 2;
    private static final int SPREAD_CHUNK_COUNT = 3;

    private static final int CLEAR_RADIUS = SPREAD_RADIUS * 2 + 1;
    private static final float DEBUG_AMP = FMLEnvironment.production? 1: 100f;


    public static void onLivingTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (player.tickCount % UPDATE_INTERVAL != 0) {
            return;
        }

        AttributeInstance bloodScentAttribute =
                player.getAttribute(SAAttributes.BLOOD_SCENT);

        if (bloodScentAttribute == null) {
            return;
        }

        float bloodScentAmp = event.getEntity().level().getCurrentDifficultyAt(event.getEntity().getOnPos()).getEffectiveDifficulty();
        int scentAmount = Mth.floor(bloodScentAttribute.getValue() * bloodScentAmp * DEBUG_AMP);

        if (scentAmount <= 0) {
            return;
        }

        ServerLevel level = player.serverLevel();
        ChunkPos playerChunk = player.chunkPosition();

        spreadBloodScent(
                level,
                playerChunk,
                scentAmount,
                level.getRandom()
        );
    }

    private static void spreadBloodScent(
            ServerLevel level,
            ChunkPos center,
            int amount,
            RandomSource random
    ) {
        Set<Integer> selectedIndices = new HashSet<>();

        int diameter = SPREAD_RADIUS * 2 + 1;
        int availableChunkCount = diameter * diameter;
        int selectionCount = Math.min(SPREAD_CHUNK_COUNT, availableChunkCount);

        while (selectedIndices.size() < selectionCount) {
            selectedIndices.add(random.nextInt(availableChunkCount));
        }

        for (int index : selectedIndices) {
            int offsetX = index % diameter - SPREAD_RADIUS;
            int offsetZ = index / diameter - SPREAD_RADIUS;

            ChunkPos targetPos = new ChunkPos(
                    center.x + offsetX,
                    center.z + offsetZ
            );

            LevelChunk targetChunk = getLoadedChunk(level, targetPos);

            if (targetChunk == null) {
                continue;
            }

            addBloodScent(level, targetChunk, amount);
        }
    }

    private static void addBloodScent(
            ServerLevel level,
            LevelChunk chunk,
            int amount
    ) {
        BloodScentCounterHelper.ifPresent(chunk, bloodScent -> {
            bloodScent.add(amount);

            if (!bloodScent.isMax()) {
                return;
            }

            ChunkPos triggeredChunk = chunk.getPos();

            if(spawnPredator(level, triggeredChunk)){
                clearNearbyBloodScent(level, triggeredChunk);
            }
        });
    }

    private static void clearNearbyBloodScent(
            ServerLevel level,
            ChunkPos center
    ) {
        for (int offsetX = -CLEAR_RADIUS; offsetX <= CLEAR_RADIUS; offsetX++) {
            for (int offsetZ = -CLEAR_RADIUS; offsetZ <= CLEAR_RADIUS; offsetZ++) {
                ChunkPos targetPos = new ChunkPos(
                        center.x + offsetX,
                        center.z + offsetZ
                );

                LevelChunk targetChunk = getLoadedChunk(level, targetPos);

                if (targetChunk == null) {
                    continue;
                }

                BloodScentCounterHelper.ifPresent(
                        targetChunk,
                        IChunkCounter::clear
                );
            }
        }
    }

    private static boolean spawnPredator(
            ServerLevel level,
            ChunkPos chunkPos
    ) {
        int blockX = chunkPos.getMiddleBlockX();
        int blockZ = chunkPos.getMiddleBlockZ();

        int blockY = level.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                blockX,
                blockZ
        );

        boolean hasNearByPlayer = level.hasNearbyAlivePlayer(blockX, blockY, blockZ, 32);
        if(hasNearByPlayer){

            return false;
        }

        EntityType<?> predatorType = TFCPredatorHelper
                .selectLocalPredator(level, new BlockPos(blockX, blockY, blockZ), level.getRandom())
                .orElse(null);

        if (predatorType == null) {
            SurvivorsAbilities.LOGGER.warn(
                    "Failed to find viable predator type for blood scent event in chunk [{}, {}]",
                    chunkPos.x,
                    chunkPos.z
            );
            return false;
        }

        Entity predator = predatorType.create(level);
        if (predator == null) {
            SurvivorsAbilities.LOGGER.error(
                    "Failed to create predator for blood scent event in chunk [{}, {}]",
                    chunkPos.x,
                    chunkPos.z
            );
            return false;
        }

        predator.moveTo(
                blockX + 0.5D,
                blockY,
                blockZ + 0.5D,
                level.getRandom().nextFloat() * 360.0F,
                0.0F
        );

        boolean added = level.addFreshEntity(predator);

        if (added) {
            SurvivorsAbilities.LOGGER.info(
                    "Spawned blood predator stand at [{}, {}, {}] in chunk [{}, {}]",
                    predator.getX(),
                    predator.getY(),
                    predator.getZ(),
                    chunkPos.x,
                    chunkPos.z
            );
            return true;
        } else {
            SurvivorsAbilities.LOGGER.warn(
                    "Failed to add blood scent predator at [{}, {}, {}]",
                    predator.getX(),
                    predator.getY(),
                    predator.getZ()
            );
            return false;
        }
    }

    private static LevelChunk getLoadedChunk(
            ServerLevel level,
            ChunkPos chunkPos
    ) {
        return level.getChunkSource().getChunkNow(
                chunkPos.x,
                chunkPos.z
        );
    }
}