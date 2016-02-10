package com.github.ustc_zzzz.fmltutor.worldgen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGeneratorLoader
{
    public static IWorldGenerator glowstoneGenerator;

    public WorldGeneratorLoader()
    {
        glowstoneGenerator = new IWorldGenerator()
        {
            public final WorldGenMinable glowstoneGenerator = new WorldGenMinable(Blocks.glowstone.getDefaultState(), 16);

            @Override
            public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
                    IChunkProvider chunkProvider)
            {
                if (world.provider.getDimensionId() == 0)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = new BlockPos(chunkX * 16 + random.nextInt(16), 16 + random.nextInt(16),
                                chunkZ * 16 + random.nextInt(16));
                        BiomeGenBase biomeGenBase = world.getBiomeGenForCoords(blockpos);
                        if (biomeGenBase.getFloatRainfall() < random.nextFloat())
                        {
                            glowstoneGenerator.generate(world, random, blockpos);
                        }
                    }
                }
            }
        };
        GameRegistry.registerWorldGenerator(glowstoneGenerator, 1);
    }

}
