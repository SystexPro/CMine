package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class PlayerManager
{

    public PlayerManager(MinecraftServer minecraftserver)
    {
        players = new ArrayList();
        playerInstances = new PlayerHash();
        playerInstancesToUpdate = new ArrayList();
        mcServer = minecraftserver;
    }

    public void updatePlayerInstances()
    {
        for(int i = 0; i < playerInstancesToUpdate.size(); i++)
        {
            ((PlayerInstance)playerInstancesToUpdate.get(i)).onUpdate();
        }

        playerInstancesToUpdate.clear();
    }

    private PlayerInstance getPlayerInstance(int i, int j, boolean flag)
    {
        long l = (long)i + 0x7fffffffL | (long)j + 0x7fffffffL << 32;
        PlayerInstance playerinstance = (PlayerInstance)playerInstances.getValueByKey(l);
        if(playerinstance == null && flag)
        {
            playerinstance = new PlayerInstance(this, i, j);
            playerInstances.add(l, playerinstance);
        }
        return playerinstance;
    }

    public void markBlockNeedsUpdate(int i, int j, int k)
    {
        int l = i >> 4;
        int i1 = k >> 4;
        PlayerInstance playerinstance = getPlayerInstance(l, i1, false);
        if(playerinstance != null)
        {
            playerinstance.markBlockNeedsUpdate(i & 0xf, j, k & 0xf);
        }
    }

    public void addPlayer(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.posX >> 4;
        int j = (int)entityplayermp.posZ >> 4;
        entityplayermp.field_9155_d = entityplayermp.posX;
        entityplayermp.field_9154_e = entityplayermp.posZ;
        int k = 0;
        byte byte0 = 10;
        int l = 0;
        int i1 = 0;
        getPlayerInstance(i, j, true).addPlayer(entityplayermp);
        for(int j1 = 1; j1 <= byte0 * 2; j1++)
        {
            for(int l1 = 0; l1 < 2; l1++)
            {
                int ai[] = field_22089_e[k++ % 4];
                for(int i2 = 0; i2 < j1; i2++)
                {
                    l += ai[0];
                    i1 += ai[1];
                    getPlayerInstance(i + l, j + i1, true).addPlayer(entityplayermp);
                }

            }

        }

        k %= 4;
        for(int k1 = 0; k1 < byte0 * 2; k1++)
        {
            l += field_22089_e[k][0];
            i1 += field_22089_e[k][1];
            getPlayerInstance(i + l, j + i1, true).addPlayer(entityplayermp);
        }

        players.add(entityplayermp);
    }

    public void removePlayer(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.field_9155_d >> 4;
        int j = (int)entityplayermp.field_9154_e >> 4;
        for(int k = i - 10; k <= i + 10; k++)
        {
            for(int l = j - 10; l <= j + 10; l++)
            {
                PlayerInstance playerinstance = getPlayerInstance(k, l, false);
                if(playerinstance != null)
                {
                    playerinstance.removePlayer(entityplayermp);
                }
            }

        }

        players.remove(entityplayermp);
    }

    private boolean func_544_a(int i, int j, int k, int l)
    {
        int i1 = i - k;
        int j1 = j - l;
        if(i1 < -10 || i1 > 10)
        {
            return false;
        }
        return j1 >= -10 && j1 <= 10;
    }

    public void func_543_c(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.posX >> 4;
        int j = (int)entityplayermp.posZ >> 4;
        double d = entityplayermp.field_9155_d - entityplayermp.posX;
        double d1 = entityplayermp.field_9154_e - entityplayermp.posZ;
        double d2 = d * d + d1 * d1;
        if(d2 < 64D)
        {
            return;
        }
        int k = (int)entityplayermp.field_9155_d >> 4;
        int l = (int)entityplayermp.field_9154_e >> 4;
        int i1 = i - k;
        int j1 = j - l;
        if(i1 == 0 && j1 == 0)
        {
            return;
        }
        for(int k1 = i - 10; k1 <= i + 10; k1++)
        {
            for(int l1 = j - 10; l1 <= j + 10; l1++)
            {
                if(!func_544_a(k1, l1, k, l))
                {
                    getPlayerInstance(k1, l1, true).addPlayer(entityplayermp);
                }
                if(func_544_a(k1 - i1, l1 - j1, i, j))
                {
                    continue;
                }
                PlayerInstance playerinstance = getPlayerInstance(k1 - i1, l1 - j1, false);
                if(playerinstance != null)
                {
                    playerinstance.removePlayer(entityplayermp);
                }
            }

        }

        entityplayermp.field_9155_d = entityplayermp.posX;
        entityplayermp.field_9154_e = entityplayermp.posZ;
    }

    public int getMaxTrackingDistance()
    {
        return 144;
    }

    static MinecraftServer getMinecraftServer(PlayerManager playermanager)
    {
        return playermanager.mcServer;
    }

    static PlayerHash getPlayerInstances(PlayerManager playermanager)
    {
        return playermanager.playerInstances;
    }

    static List getPlayerInstancesToUpdate(PlayerManager playermanager)
    {
        return playermanager.playerInstancesToUpdate;
    }

    private List players;
    private PlayerHash playerInstances;
    private List playerInstancesToUpdate;
    private MinecraftServer mcServer;
    private final int field_22089_e[][] = {
        {
            1, 0
        }, {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }
    };
}
