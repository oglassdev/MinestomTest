package com.hotslicerrpg.server.worlds;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Generators {
    public static class SingleBlockGenerator implements ChunkGenerator {
        @Override
        public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
            if (chunkX == 0 && chunkZ == 0) {
                batch.setBlock(0,39,0, Block.GRASS_BLOCK);
            }
            /* Set chunk blocks
            for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                    for (byte y = 0; y < 40; y++) {
                        if (x == 0 && z == 0) {
                            batch.setBlock(x,y,z,Block.BEDROCK);
                        }
                    }
                }
            }*/
        }

        @Override
        public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
            Arrays.fill(biomes, Biome.PLAINS);
        }

        @Override
        public List<ChunkPopulator> getPopulators() {
            return null;
        }
    }
    public static class StoneGenerator implements ChunkGenerator {
        @Override
        public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
            // Set chunk blocks
            for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                    for (byte y = 0; y < 40; y++) {
                        batch.setBlock(x,y,z,Block.STONE);
                    }
                }
            }
        }

        @Override
        public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
            Arrays.fill(biomes, Biome.PLAINS);
        }

        @Override
        public List<ChunkPopulator> getPopulators() {
            return null;
        }
    }
}
