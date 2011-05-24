package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockSapling extends BlockFlower
{

    protected BlockSapling(int i, int j)
    {
        super(i, j);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
        if(world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(30) == 0)
        {
            int l = world.getBlockMetadata(i, j, k);
            if((l & 8) == 0)
            {
                world.setBlockMetadataWithNotify(i, j, k, l | 8);
            } else
            {
                func_21027_b(world, i, j, k, random);
            }
        }
    }

    public int func_22009_a(int i, int j)
    {
        j &= 3;
        if(j == 1)
        {
            return 63;
        }
        if(j == 2)
        {
            return 79;
        } else
        {
            return super.func_22009_a(i, j);
        }
    }

    public void func_21027_b(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMetadata(i, j, k) & 3;
        world.setBlock(i, j, k, 0);
        Object obj = null;
        if(l == 1)
        {
            obj = new WorldGenTaiga2();
        } else
        if(l == 2)
        {
            obj = new WorldGenForest();
        } else
        {
            obj = new WorldGenTrees();
            if(random.nextInt(10) == 0)
            {
                obj = new WorldGenBigTree();
            }
        }
        if(!((WorldGenerator) (obj)).generate(world, random, i, j, k))
        {
            world.setBlockAndMetadata(i, j, k, blockID, l);
        }
    }

    protected int damageDropped(int i)
    {
        return i & 3;
    }
}
