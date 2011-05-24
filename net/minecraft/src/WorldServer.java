package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class WorldServer extends World
{

    public WorldServer(MinecraftServer minecraftserver, ISaveHandler isavehandler, String s, int i, long l)
    {
        super(isavehandler, s, l, WorldProvider.func_4091_a(i));
        field_819_z = false;
        field_20912_E = new MCHash();
        mcServer = minecraftserver;
    }

    public void updateEntityWithOptionalForce(Entity entity, boolean flag)
    {
        if(!mcServer.spawnPeacefulMobs && ((entity instanceof EntityAnimal) || (entity instanceof EntityWaterMob)))
        {
            entity.setEntityDead();
        }
        if(entity.riddenByEntity == null || !(entity.riddenByEntity instanceof EntityPlayer))
        {
            super.updateEntityWithOptionalForce(entity, flag);
        }
    }

    public void func_12017_b(Entity entity, boolean flag)
    {
        super.updateEntityWithOptionalForce(entity, flag);
    }

    protected IChunkProvider func_22086_b()
    {
        IChunkLoader ichunkloader = worldFile.func_22092_a(worldProvider);
        chunkProvider = new ChunkProviderServer(this, ichunkloader, worldProvider.getChunkProvider());
        return chunkProvider;
    }

    public List getTileEntityList(int i, int j, int k, int l, int i1, int j1)
    {
        ArrayList arraylist = new ArrayList();
        for(int k1 = 0; k1 < loadedTileEntityList.size(); k1++)
        {
            TileEntity tileentity = (TileEntity)loadedTileEntityList.get(k1);
            if(tileentity.xCoord >= i && tileentity.yCoord >= j && tileentity.zCoord >= k && tileentity.xCoord < l && tileentity.yCoord < i1 && tileentity.zCoord < j1)
            {
                arraylist.add(tileentity);
            }
        }

        return arraylist;
    }

    public boolean canMineBlock(EntityPlayer entityplayer, int i, int j, int k)
    {
        int l = (int)MathHelper.abs(i - worldInfo.getSpawnX());
        int i1 = (int)MathHelper.abs(k - worldInfo.getSpawnZ());
        if(l > i1)
        {
            i1 = l;
        }
        return i1 > 16 || mcServer.configManager.isOp(entityplayer.username);
    }

    protected void obtainEntitySkin(Entity entity)
    {
        super.obtainEntitySkin(entity);
        field_20912_E.addKey(entity.entityId, entity);
    }

    protected void releaseEntitySkin(Entity entity)
    {
        super.releaseEntitySkin(entity);
        field_20912_E.removeObject(entity.entityId);
    }

    public Entity func_6158_a(int i)
    {
        return (Entity)field_20912_E.lookup(i);
    }

    public boolean func_27073_a(Entity entity)
    {
        if(super.func_27073_a(entity))
        {
            mcServer.configManager.sendPacketToPlayersAroundPoint(entity.posX, entity.posY, entity.posZ, 512D, new Packet71Weather(entity));
            return true;
        } else
        {
            return false;
        }
    }

    public void func_9206_a(Entity entity, byte byte0)
    {
        Packet38EntityStatus packet38entitystatus = new Packet38EntityStatus(entity.entityId, byte0);
        mcServer.entityTracker.sendPacketToTrackedPlayersAndTrackedEntity(entity, packet38entitystatus);
    }

    public Explosion newExplosion(Entity entity, double d, double d1, double d2, 
            float f, boolean flag)
    {
        Explosion explosion = new Explosion(this, entity, d, d1, d2, f);
        explosion.isFlaming = flag;
        explosion.doExplosion();
        explosion.doEffects(false);
        mcServer.configManager.sendPacketToPlayersAroundPoint(d, d1, d2, 64D, new Packet60Explosion(d, d1, d2, f, explosion.destroyedBlockPositions));
        return explosion;
    }

    public void playNoteAt(int i, int j, int k, int l, int i1)
    {
        super.playNoteAt(i, j, k, l, i1);
        mcServer.configManager.sendPacketToPlayersAroundPoint(i, j, k, 64D, new Packet54PlayNoteBlock(i, j, k, l, i1));
    }

    public void func_27082_w()
    {
        worldFile.func_22093_e();
    }

    protected void func_27066_i()
    {
        boolean flag = func_27068_v();
        super.func_27066_i();
        if(flag != func_27068_v())
        {
            if(flag)
            {
                mcServer.configManager.sendPacketToAllPlayers(new Packet70Bed(2));
            } else
            {
                mcServer.configManager.sendPacketToAllPlayers(new Packet70Bed(1));
            }
        }
    }

    public ChunkProviderServer chunkProvider;
    public boolean field_819_z;
    public boolean levelSaving;
    private MinecraftServer mcServer;
    private MCHash field_20912_E;
}
