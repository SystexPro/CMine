package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BiomeGenRainforest extends BiomeGenBase
{

    public BiomeGenRainforest()
    {
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random)
    {
        if(random.nextInt(3) == 0)
        {
            return new WorldGenBigTree();
        } else
        {
            return new WorldGenTrees();
        }
    }
}
