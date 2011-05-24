package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public class BiomeGenBase
{

    protected BiomeGenBase()
    {
        topBlock = (byte)Block.grass.blockID;
        fillerBlock = (byte)Block.dirt.blockID;
        field_6161_q = 0x4ee031;
        field_25058_r = new ArrayList();
        field_25057_s = new ArrayList();
        field_25056_t = new ArrayList();
        field_27050_v = true;
        field_25058_r.add(new SpawnListEntry(EntitySpider.class, 10));
        field_25058_r.add(new SpawnListEntry(EntityZombie.class, 10));
        field_25058_r.add(new SpawnListEntry(EntitySkeleton.class, 10));
        field_25058_r.add(new SpawnListEntry(EntityCreeper.class, 10));
        field_25058_r.add(new SpawnListEntry(EntitySlime.class, 10));
        field_25057_s.add(new SpawnListEntry(EntitySheep.class, 12));
        field_25057_s.add(new SpawnListEntry(EntityPig.class, 10));
        field_25057_s.add(new SpawnListEntry(EntityChicken.class, 10));
        field_25057_s.add(new SpawnListEntry(EntityCow.class, 8));
        field_25056_t.add(new SpawnListEntry(EntitySquid.class, 10));
    }

    private BiomeGenBase func_27047_e()
    {
        field_27050_v = false;
        return this;
    }

    public static void generateBiomeLookup()
    {
        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
            {
                biomeLookupTable[i + j * 64] = getBiome((float)i / 63F, (float)j / 63F);
            }

        }

        desert.topBlock = desert.fillerBlock = (byte)Block.sand.blockID;
        iceDesert.topBlock = iceDesert.fillerBlock = (byte)Block.sand.blockID;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random)
    {
        if(random.nextInt(10) == 0)
        {
            return new WorldGenBigTree();
        } else
        {
            return new WorldGenTrees();
        }
    }

    protected BiomeGenBase doesNothingForMobSpawnerBase()
    {
        field_27051_u = true;
        return this;
    }

    protected BiomeGenBase setBiomeName(String s)
    {
        biomeName = s;
        return this;
    }

    protected BiomeGenBase func_4080_a(int i)
    {
        field_6161_q = i;
        return this;
    }

    protected BiomeGenBase setColor(int i)
    {
        color = i;
        return this;
    }

    public static BiomeGenBase getBiomeFromLookup(double d, double d1)
    {
        int i = (int)(d * 63D);
        int j = (int)(d1 * 63D);
        return biomeLookupTable[i + j * 64];
    }

    public static BiomeGenBase getBiome(float f, float f1)
    {
        f1 *= f;
        if(f < 0.1F)
        {
            return tundra;
        }
        if(f1 < 0.2F)
        {
            if(f < 0.5F)
            {
                return tundra;
            }
            if(f < 0.95F)
            {
                return savanna;
            } else
            {
                return desert;
            }
        }
        if(f1 > 0.5F && f < 0.7F)
        {
            return swampland;
        }
        if(f < 0.5F)
        {
            return taiga;
        }
        if(f < 0.97F)
        {
            if(f1 < 0.35F)
            {
                return shrubland;
            } else
            {
                return forest;
            }
        }
        if(f1 < 0.45F)
        {
            return plains;
        }
        if(f1 < 0.9F)
        {
            return seasonalForest;
        } else
        {
            return rainforest;
        }
    }

    public List func_25055_a(EnumCreatureType enumcreaturetype)
    {
        if(enumcreaturetype == EnumCreatureType.monster)
        {
            return field_25058_r;
        }
        if(enumcreaturetype == EnumCreatureType.creature)
        {
            return field_25057_s;
        }
        if(enumcreaturetype == EnumCreatureType.waterCreature)
        {
            return field_25056_t;
        } else
        {
            return null;
        }
    }

    public boolean func_27049_c()
    {
        return field_27051_u;
    }

    public boolean func_27048_d()
    {
        if(field_27051_u)
        {
            return false;
        } else
        {
            return field_27050_v;
        }
    }

    public static final BiomeGenBase rainforest = (new BiomeGenRainforest()).setColor(0x8fa36).setBiomeName("Rainforest").func_4080_a(0x1ff458);
    public static final BiomeGenBase swampland = (new BiomeGenSwamp()).setColor(0x7f9b2).setBiomeName("Swampland").func_4080_a(0x8baf48);
    public static final BiomeGenBase seasonalForest = (new BiomeGenBase()).setColor(0x9be023).setBiomeName("Seasonal Forest");
    public static final BiomeGenBase forest = (new BiomeGenForest()).setColor(0x56621).setBiomeName("Forest").func_4080_a(0x4eba31);
    public static final BiomeGenBase savanna = (new BiomeGenDesert()).setColor(0xd9e023).setBiomeName("Savanna");
    public static final BiomeGenBase shrubland = (new BiomeGenBase()).setColor(0xa1ad20).setBiomeName("Shrubland");
    public static final BiomeGenBase taiga = (new BiomeGenTaiga()).setColor(0x2eb153).setBiomeName("Taiga").doesNothingForMobSpawnerBase().func_4080_a(0x7bb731);
    public static final BiomeGenBase desert = (new BiomeGenDesert()).setColor(0xfa9418).setBiomeName("Desert").func_27047_e();
    public static final BiomeGenBase plains = (new BiomeGenDesert()).setColor(0xffd910).setBiomeName("Plains");
    public static final BiomeGenBase iceDesert = (new BiomeGenDesert()).setColor(0xffed93).setBiomeName("Ice Desert").doesNothingForMobSpawnerBase().func_27047_e().func_4080_a(0xc4d339);
    public static final BiomeGenBase tundra = (new BiomeGenBase()).setColor(0x57ebf9).setBiomeName("Tundra").doesNothingForMobSpawnerBase().func_4080_a(0xc4d339);
    public static final BiomeGenBase hell = (new BiomeGenHell()).setColor(0xff0000).setBiomeName("Hell").func_27047_e();
    public String biomeName;
    public int color;
    public byte topBlock;
    public byte fillerBlock;
    public int field_6161_q;
    protected List field_25058_r;
    protected List field_25057_s;
    protected List field_25056_t;
    private boolean field_27051_u;
    private boolean field_27050_v;
    private static BiomeGenBase biomeLookupTable[] = new BiomeGenBase[4096];

    static 
    {
        generateBiomeLookup();
    }
}
