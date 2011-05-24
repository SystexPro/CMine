package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer
    implements ICrafting
{

    public EntityPlayerMP(MinecraftServer minecraftserver, World world, String s, ItemInWorldManager iteminworldmanager)
    {
        super(world);
        loadedChunks = new LinkedList();
        field_420_ah = new HashSet();
        lastHealth = 0xfa0a1f01;
        ticksOfInvuln = 60;
        currentWindowId = 0;
        ChunkCoordinates chunkcoordinates = world.getSpawnPoint();
        int i = chunkcoordinates.posX;
        int j = chunkcoordinates.posZ;
        int k = chunkcoordinates.posY;
        if(!world.worldProvider.field_4306_c)
        {
            i += rand.nextInt(20) - 10;
            k = world.findTopSolidBlock(i, j);
            j += rand.nextInt(20) - 10;
        }
        setLocationAndAngles((double)i + 0.5D, k, (double)j + 0.5D, 0.0F, 0.0F);
        mcServer = minecraftserver;
        stepHeight = 0.0F;
        iteminworldmanager.thisPlayer = this;
        username = s;
        itemInWorldManager = iteminworldmanager;
        yOffset = 0.0F;
    }

    public void func_20057_k()
    {
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public ItemStack[] getInventory()
    {
        return playerInventory;
    }

    protected void resetHeight()
    {
        yOffset = 0.0F;
    }

    public float getEyeHeight()
    {
        return 1.62F;
    }

    public void onUpdate()
    {
        itemInWorldManager.func_328_a();
        ticksOfInvuln--;
        currentCraftingInventory.updateCraftingMatrix();
        for(int i = 0; i < 5; i++)
        {
            ItemStack itemstack = getEquipmentInSlot(i);
            if(itemstack != playerInventory[i])
            {
                mcServer.entityTracker.sendPacketToTrackedPlayers(this, new Packet5PlayerInventory(entityId, i, itemstack));
                playerInventory[i] = itemstack;
            }
        }

    }

    public ItemStack getEquipmentInSlot(int i)
    {
        if(i == 0)
        {
            return inventory.getCurrentItem();
        } else
        {
            return inventory.armorInventory[i - 1];
        }
    }

    public void onDeath(Entity entity)
    {
        inventory.dropAllItems();
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        if(ticksOfInvuln > 0)
        {
            return false;
        }
        if(!mcServer.pvpOn)
        {
            if(entity instanceof EntityPlayer)
            {
                return false;
            }
            if(entity instanceof EntityArrow)
            {
                EntityArrow entityarrow = (EntityArrow)entity;
                if(entityarrow.owner instanceof EntityPlayer)
                {
                    return false;
                }
            }
        }
        return super.attackEntityFrom(entity, i);
    }

    protected boolean func_27016_t()
    {
        return mcServer.pvpOn;
    }

    public void heal(int i)
    {
        super.heal(i);
    }

    public void onUpdateEntity(boolean flag)
    {
        super.onUpdate();
        if(flag && !loadedChunks.isEmpty())
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)loadedChunks.get(0);
            if(chunkcoordintpair != null)
            {
                boolean flag1 = false;
                if(playerNetServerHandler.getNumChunkDataPackets() < 2)
                {
                    flag1 = true;
                }
                if(flag1)
                {
                    loadedChunks.remove(chunkcoordintpair);
                    playerNetServerHandler.sendPacket(new Packet51MapChunk(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPos * 16, 16, 128, 16, mcServer.worldMngr));
                    List list = mcServer.worldMngr.getTileEntityList(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPos * 16, chunkcoordintpair.chunkXPos * 16 + 16, 128, chunkcoordintpair.chunkZPos * 16 + 16);
                    for(int i = 0; i < list.size(); i++)
                    {
                        getTileEntityInfo((TileEntity)list.get(i));
                    }

                }
            }
        }
        if(health != lastHealth)
        {
            playerNetServerHandler.sendPacket(new Packet8UpdateHealth(health));
            lastHealth = health;
        }
    }

    private void getTileEntityInfo(TileEntity tileentity)
    {
        if(tileentity != null)
        {
            Packet packet = tileentity.getDescriptionPacket();
            if(packet != null)
            {
                playerNetServerHandler.sendPacket(packet);
            }
        }
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    public void onItemPickup(Entity entity, int i)
    {
        if(!entity.isDead)
        {
            if(entity instanceof EntityItem)
            {
                mcServer.entityTracker.sendPacketToTrackedPlayers(entity, new Packet22Collect(entity.entityId, entityId));
            }
            if(entity instanceof EntityArrow)
            {
                mcServer.entityTracker.sendPacketToTrackedPlayers(entity, new Packet22Collect(entity.entityId, entityId));
            }
        }
        super.onItemPickup(entity, i);
        currentCraftingInventory.updateCraftingMatrix();
    }

    public void swingItem()
    {
        if(!isSwinging)
        {
            swingProgressInt = -1;
            isSwinging = true;
            mcServer.entityTracker.sendPacketToTrackedPlayers(this, new Packet18Animation(this, 1));
        }
    }

    public void func_22068_s()
    {
    }

    public EnumStatus goToSleep(int i, int j, int k)
    {
        EnumStatus enumstatus = super.goToSleep(i, j, k);
        if(enumstatus == EnumStatus.OK)
        {
            mcServer.entityTracker.sendPacketToTrackedPlayers(this, new Packet17Sleep(this, 0, i, j, k));
        }
        return enumstatus;
    }

    public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2)
    {
        if(isPlayerSleeping())
        {
            mcServer.entityTracker.sendPacketToTrackedPlayersAndTrackedEntity(this, new Packet18Animation(this, 3));
        }
        super.wakeUpPlayer(flag, flag1, flag2);
        playerNetServerHandler.teleportTo(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    public void mountEntity(Entity entity)
    {
        super.mountEntity(entity);
        playerNetServerHandler.sendPacket(new Packet39AttachEntity(this, ridingEntity));
        playerNetServerHandler.teleportTo(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    protected void updateFallState(double d, boolean flag)
    {
    }

    public void handleFalling(double d, boolean flag)
    {
        super.updateFallState(d, flag);
    }

    private void getNextWidowId()
    {
        currentWindowId = currentWindowId % 100 + 1;
    }

    public void displayWorkbenchGUI(int i, int j, int k)
    {
        getNextWidowId();
        playerNetServerHandler.sendPacket(new Packet100OpenWindow(currentWindowId, 1, "Crafting", 9));
        currentCraftingInventory = new ContainerWorkbench(inventory, worldObj, i, j, k);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void displayGUIChest(IInventory iinventory)
    {
        getNextWidowId();
        playerNetServerHandler.sendPacket(new Packet100OpenWindow(currentWindowId, 0, iinventory.getInvName(), iinventory.getSizeInventory()));
        currentCraftingInventory = new ContainerChest(inventory, iinventory);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void displayGUIFurnace(TileEntityFurnace tileentityfurnace)
    {
        getNextWidowId();
        playerNetServerHandler.sendPacket(new Packet100OpenWindow(currentWindowId, 2, tileentityfurnace.getInvName(), tileentityfurnace.getSizeInventory()));
        currentCraftingInventory = new ContainerFurnace(inventory, tileentityfurnace);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void displayGUIDispenser(TileEntityDispenser tileentitydispenser)
    {
        getNextWidowId();
        playerNetServerHandler.sendPacket(new Packet100OpenWindow(currentWindowId, 3, tileentitydispenser.getInvName(), tileentitydispenser.getSizeInventory()));
        currentCraftingInventory = new ContainerDispenser(inventory, tileentitydispenser);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void updateCraftingInventorySlot(Container container, int i, ItemStack itemstack)
    {
        if(container.getSlot(i) instanceof SlotCrafting)
        {
            return;
        }
        if(isChangingQuantityOnly)
        {
            return;
        } else
        {
            playerNetServerHandler.sendPacket(new Packet103SetSlot(container.windowId, i, itemstack));
            return;
        }
    }

    public void updateCraftingInventory(Container container, List list)
    {
        playerNetServerHandler.sendPacket(new Packet104WindowItems(container.windowId, list));
        playerNetServerHandler.sendPacket(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
    }

    public void updateCraftingInventoryInfo(Container container, int i, int j)
    {
        playerNetServerHandler.sendPacket(new Packet105UpdateProgressbar(container.windowId, i, j));
    }

    public void onItemStackChanged(ItemStack itemstack)
    {
    }

    public void usePersonalCraftingInventory()
    {
        playerNetServerHandler.sendPacket(new Packet101CloseWindow(currentCraftingInventory.windowId));
        closeCraftingGui();
    }

    public void updateHeldItem()
    {
        if(isChangingQuantityOnly)
        {
            return;
        } else
        {
            playerNetServerHandler.sendPacket(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
            return;
        }
    }

    public void closeCraftingGui()
    {
        currentCraftingInventory.onCraftGuiClosed(this);
        currentCraftingInventory = personalCraftingInventory;
    }

    public void setMovementType(float f, float f1, boolean flag, boolean flag1, float f2, float f3)
    {
        moveStrafing = f;
        moveForward = f1;
        isJumping = flag;
        func_21043_b(flag1);
        rotationPitch = f2;
        rotationYaw = f3;
    }

    public void func_25046_a(StatBase statbase, int i)
    {
        if(statbase == null)
        {
            return;
        }
        if(!statbase.field_27058_g)
        {
            for(; i > 100; i -= 100)
            {
                playerNetServerHandler.sendPacket(new Packet200Statistic(statbase.field_25063_d, 100));
            }

            playerNetServerHandler.sendPacket(new Packet200Statistic(statbase.field_25063_d, i));
        }
    }

    public NetServerHandler playerNetServerHandler;
    public MinecraftServer mcServer;
    public ItemInWorldManager itemInWorldManager;
    public double field_9155_d;
    public double field_9154_e;
    public List loadedChunks;
    public Set field_420_ah;
    private int lastHealth;
    private int ticksOfInvuln;
    private ItemStack playerInventory[] = {
        null, null, null, null, null
    };
    private int currentWindowId;
    public boolean isChangingQuantityOnly;
}
