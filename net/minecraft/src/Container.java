package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public abstract class Container
{

    public Container()
    {
        inventoryItemStacks = new ArrayList();
        inventorySlots = new ArrayList();
        windowId = 0;
        field_20132_a = 0;
        crafters = new ArrayList();
        field_20131_b = new HashSet();
    }

    protected void addSlot(Slot slot)
    {
        slot.id = inventorySlots.size();
        inventorySlots.add(slot);
        inventoryItemStacks.add(null);
    }

    public void onCraftGuiOpened(ICrafting icrafting)
    {
        crafters.add(icrafting);
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < inventorySlots.size(); i++)
        {
            arraylist.add(((Slot)inventorySlots.get(i)).getStack());
        }

        icrafting.updateCraftingInventory(this, arraylist);
        updateCraftingMatrix();
    }

    public void updateCraftingMatrix()
    {
        for(int i = 0; i < inventorySlots.size(); i++)
        {
            ItemStack itemstack = ((Slot)inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack)inventoryItemStacks.get(i);
            if(ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
                continue;
            }
            itemstack1 = itemstack != null ? itemstack.copy() : null;
            inventoryItemStacks.set(i, itemstack1);
            for(int j = 0; j < crafters.size(); j++)
            {
                ((ICrafting)crafters.get(j)).updateCraftingInventorySlot(this, i, itemstack1);
            }

        }

    }

    public Slot func_20127_a(IInventory iinventory, int i)
    {
        for(int j = 0; j < inventorySlots.size(); j++)
        {
            Slot slot = (Slot)inventorySlots.get(j);
            if(slot.isHere(iinventory, i))
            {
                return slot;
            }
        }

        return null;
    }

    public Slot getSlot(int i)
    {
        return (Slot)inventorySlots.get(i);
    }

    public ItemStack func_27086_a(int i)
    {
        Slot slot = (Slot)inventorySlots.get(i);
        if(slot != null)
        {
            return slot.getStack();
        } else
        {
            return null;
        }
    }

    public ItemStack func_27085_a(int i, int j, boolean flag, EntityPlayer entityplayer)
    {
        ItemStack itemstack = null;
        if(j == 0 || j == 1)
        {
            InventoryPlayer inventoryplayer = entityplayer.inventory;
            if(i == -999)
            {
                if(inventoryplayer.getItemStack() != null && i == -999)
                {
                    if(j == 0)
                    {
                        entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
                        inventoryplayer.setItemStack(null);
                    }
                    if(j == 1)
                    {
                        entityplayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));
                        if(inventoryplayer.getItemStack().stackSize == 0)
                        {
                            inventoryplayer.setItemStack(null);
                        }
                    }
                }
            } else
            if(flag)
            {
                ItemStack itemstack1 = func_27086_a(i);
                if(itemstack1 != null)
                {
                    itemstack = itemstack1.copy();
                }
            } else
            {
                Slot slot = (Slot)inventorySlots.get(i);
                if(slot != null)
                {
                    slot.onSlotChanged();
                    ItemStack itemstack2 = slot.getStack();
                    ItemStack itemstack3 = inventoryplayer.getItemStack();
                    if(itemstack2 != null)
                    {
                        itemstack = itemstack2.copy();
                    }
                    if(itemstack2 == null)
                    {
                        if(itemstack3 != null && slot.isItemValid(itemstack3))
                        {
                            int k = j != 0 ? 1 : itemstack3.stackSize;
                            if(k > slot.getSlotStackLimit())
                            {
                                k = slot.getSlotStackLimit();
                            }
                            slot.putStack(itemstack3.splitStack(k));
                            if(itemstack3.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                        }
                    } else
                    if(itemstack3 == null)
                    {
                        int l = j != 0 ? (itemstack2.stackSize + 1) / 2 : itemstack2.stackSize;
                        ItemStack itemstack5 = slot.decrStackSize(l);
                        if(itemstack5 != null && slot.func_25003_d())
                        {
                            entityplayer.func_25046_a(StatList.field_25093_z[itemstack5.itemID], itemstack5.stackSize);
                        }
                        inventoryplayer.setItemStack(itemstack5);
                        if(itemstack2.stackSize == 0)
                        {
                            slot.putStack(null);
                        }
                        slot.onPickupFromSlot(inventoryplayer.getItemStack());
                    } else
                    if(slot.isItemValid(itemstack3))
                    {
                        if(itemstack2.itemID != itemstack3.itemID || itemstack2.getHasSubtypes() && itemstack2.getItemDamage() != itemstack3.getItemDamage())
                        {
                            if(itemstack3.stackSize <= slot.getSlotStackLimit())
                            {
                                ItemStack itemstack4 = itemstack2;
                                slot.putStack(itemstack3);
                                inventoryplayer.setItemStack(itemstack4);
                            }
                        } else
                        {
                            int i1 = j != 0 ? 1 : itemstack3.stackSize;
                            if(i1 > slot.getSlotStackLimit() - itemstack2.stackSize)
                            {
                                i1 = slot.getSlotStackLimit() - itemstack2.stackSize;
                            }
                            if(i1 > itemstack3.getMaxStackSize() - itemstack2.stackSize)
                            {
                                i1 = itemstack3.getMaxStackSize() - itemstack2.stackSize;
                            }
                            itemstack3.splitStack(i1);
                            if(itemstack3.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                            itemstack2.stackSize += i1;
                        }
                    } else
                    if(itemstack2.itemID == itemstack3.itemID && itemstack3.getMaxStackSize() > 1 && (!itemstack2.getHasSubtypes() || itemstack2.getItemDamage() == itemstack3.getItemDamage()))
                    {
                        int j1 = itemstack2.stackSize;
                        if(j1 > 0 && j1 + itemstack3.stackSize <= itemstack3.getMaxStackSize())
                        {
                            itemstack3.stackSize += j1;
                            ItemStack itemstack6 = itemstack2.splitStack(j1);
                            if(itemstack6 != null && slot.func_25003_d())
                            {
                                entityplayer.func_25046_a(StatList.field_25093_z[itemstack6.itemID], itemstack6.stackSize);
                            }
                            if(itemstack2.stackSize == 0)
                            {
                                slot.putStack(null);
                            }
                            slot.onPickupFromSlot(inventoryplayer.getItemStack());
                        }
                    }
                }
            }
        }
        return itemstack;
    }

    public void onCraftGuiClosed(EntityPlayer entityplayer)
    {
        InventoryPlayer inventoryplayer = entityplayer.inventory;
        if(inventoryplayer.getItemStack() != null)
        {
            entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
            inventoryplayer.setItemStack(null);
        }
    }

    public void onCraftMatrixChanged(IInventory iinventory)
    {
        updateCraftingMatrix();
    }

    public boolean getCanCraft(EntityPlayer entityplayer)
    {
        return !field_20131_b.contains(entityplayer);
    }

    public void setCanCraft(EntityPlayer entityplayer, boolean flag)
    {
        if(flag)
        {
            field_20131_b.remove(entityplayer);
        } else
        {
            field_20131_b.add(entityplayer);
        }
    }

    public abstract boolean canInteractWith(EntityPlayer entityplayer);

    public List inventoryItemStacks;
    public List inventorySlots;
    public int windowId;
    private short field_20132_a;
    protected List crafters;
    private Set field_20131_b;
}
