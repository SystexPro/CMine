package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class WorldInfo
{

    public WorldInfo(NBTTagCompound nbttagcompound)
    {
        randomSeed = nbttagcompound.getLong("RandomSeed");
        spawnX = nbttagcompound.getInteger("SpawnX");
        spawnY = nbttagcompound.getInteger("SpawnY");
        spawnZ = nbttagcompound.getInteger("SpawnZ");
        worldTime = nbttagcompound.getLong("Time");
        lastTimePlayed = nbttagcompound.getLong("LastPlayed");
        sizeOnDisk = nbttagcompound.getLong("SizeOnDisk");
        levelName = nbttagcompound.getString("LevelName");
        saveVersion = nbttagcompound.getInteger("version");
        field_27125_m = nbttagcompound.getInteger("rainTime");
        field_27126_l = nbttagcompound.getBoolean("raining");
        field_27123_o = nbttagcompound.getInteger("thunderTime");
        field_27124_n = nbttagcompound.getBoolean("thundering");
        if(nbttagcompound.hasKey("Player"))
        {
            field_22195_h = nbttagcompound.getCompoundTag("Player");
            field_22194_i = field_22195_h.getInteger("Dimension");
        }
    }

    public WorldInfo(long l, String s)
    {
        randomSeed = l;
        levelName = s;
    }

    public WorldInfo(WorldInfo worldinfo)
    {
        randomSeed = worldinfo.randomSeed;
        spawnX = worldinfo.spawnX;
        spawnY = worldinfo.spawnY;
        spawnZ = worldinfo.spawnZ;
        worldTime = worldinfo.worldTime;
        lastTimePlayed = worldinfo.lastTimePlayed;
        sizeOnDisk = worldinfo.sizeOnDisk;
        field_22195_h = worldinfo.field_22195_h;
        field_22194_i = worldinfo.field_22194_i;
        levelName = worldinfo.levelName;
        saveVersion = worldinfo.saveVersion;
        field_27125_m = worldinfo.field_27125_m;
        field_27126_l = worldinfo.field_27126_l;
        field_27123_o = worldinfo.field_27123_o;
        field_27124_n = worldinfo.field_27124_n;
    }

    public NBTTagCompound func_22185_a()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        saveNBTTag(nbttagcompound, field_22195_h);
        return nbttagcompound;
    }

    public NBTTagCompound func_22183_a(List list)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        EntityPlayer entityplayer = null;
        NBTTagCompound nbttagcompound1 = null;
        if(list.size() > 0)
        {
            entityplayer = (EntityPlayer)list.get(0);
        }
        if(entityplayer != null)
        {
            nbttagcompound1 = new NBTTagCompound();
            entityplayer.writeToNBT(nbttagcompound1);
        }
        saveNBTTag(nbttagcompound, nbttagcompound1);
        return nbttagcompound;
    }

    private void saveNBTTag(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1)
    {
        nbttagcompound.setLong("RandomSeed", randomSeed);
        nbttagcompound.setInteger("SpawnX", spawnX);
        nbttagcompound.setInteger("SpawnY", spawnY);
        nbttagcompound.setInteger("SpawnZ", spawnZ);
        nbttagcompound.setLong("Time", worldTime);
        nbttagcompound.setLong("SizeOnDisk", sizeOnDisk);
        nbttagcompound.setLong("LastPlayed", System.currentTimeMillis());
        nbttagcompound.setString("LevelName", levelName);
        nbttagcompound.setInteger("version", saveVersion);
        nbttagcompound.setInteger("rainTime", field_27125_m);
        nbttagcompound.setBoolean("raining", field_27126_l);
        nbttagcompound.setInteger("thunderTime", field_27123_o);
        nbttagcompound.setBoolean("thundering", field_27124_n);
        if(nbttagcompound1 != null)
        {
            nbttagcompound.setCompoundTag("Player", nbttagcompound1);
        }
    }

    public long getRandomSeed()
    {
        return randomSeed;
    }

    public int getSpawnX()
    {
        return spawnX;
    }

    public int getSpawnY()
    {
        return spawnY;
    }

    public int getSpawnZ()
    {
        return spawnZ;
    }

    public long getWorldTime()
    {
        return worldTime;
    }

    public long getSizeOnDisk()
    {
        return sizeOnDisk;
    }

    public int getDimension()
    {
        return field_22194_i;
    }

    public void setWorldTime(long l)
    {
        worldTime = l;
    }

    public void setSizeOnDisk(long l)
    {
        sizeOnDisk = l;
    }

    public void setSpawnPosition(int i, int j, int k)
    {
        spawnX = i;
        spawnY = j;
        spawnZ = k;
    }

    public void setLevelName(String s)
    {
        levelName = s;
    }

    public int getVersion()
    {
        return saveVersion;
    }

    public void setVersion(int i)
    {
        saveVersion = i;
    }

    public boolean func_27122_j()
    {
        return field_27124_n;
    }

    public void func_27119_a(boolean flag)
    {
        field_27124_n = flag;
    }

    public int func_27117_k()
    {
        return field_27123_o;
    }

    public void func_27121_b(int i)
    {
        field_27123_o = i;
    }

    public boolean func_27115_l()
    {
        return field_27126_l;
    }

    public void func_27116_b(boolean flag)
    {
        field_27126_l = flag;
    }

    public int func_27120_m()
    {
        return field_27125_m;
    }

    public void func_27118_c(int i)
    {
        field_27125_m = i;
    }

    private long randomSeed;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private long worldTime;
    private long lastTimePlayed;
    private long sizeOnDisk;
    private NBTTagCompound field_22195_h;
    private int field_22194_i;
    private String levelName;
    private int saveVersion;
    private boolean field_27126_l;
    private int field_27125_m;
    private boolean field_27124_n;
    private int field_27123_o;
}
