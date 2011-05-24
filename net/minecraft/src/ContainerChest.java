package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class ContainerChest extends Container
{

    public ContainerChest(IInventory iinventory, IInventory iinventory1)
    {
        field_20137_a = iinventory1;
        field_27088_b = iinventory1.getSizeInventory() / 9;
        int i = (field_27088_b - 4) * 18;
        for(int j = 0; j < field_27088_b; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(iinventory1, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
            }

        }

        for(int k = 0; k < 3; k++)
        {
            for(int j1 = 0; j1 < 9; j1++)
            {
                addSlot(new Slot(iinventory, j1 + k * 9 + 9, 8 + j1 * 18, 103 + k * 18 + i));
            }

        }

        for(int l = 0; l < 9; l++)
        {
            addSlot(new Slot(iinventory, l, 8 + l * 18, 161 + i));
        }

    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return field_20137_a.canInteractWith(entityplayer);
    }

    private void func_27087_a(ItemStack itemstack, int i, int j)
    {
        int k = i;
        if(itemstack.func_21132_c())
        {
            for(; itemstack.stackSize > 0 && k < j; k++)
            {
                Slot slot = (Slot)inventorySlots.get(k);
                ItemStack itemstack1 = slot.getStack();
                if(itemstack1 == null || itemstack1.itemID != itemstack.itemID || itemstack.getHasSubtypes() && itemstack.getItemDamage() != itemstack1.getItemDamage())
                {
                    continue;
                }
                int i1 = itemstack1.stackSize + itemstack.stackSize;
                if(i1 <= itemstack.getMaxStackSize())
                {
                    itemstack.stackSize = 0;
                    itemstack1.stackSize = i1;
                    slot.onSlotChanged();
                    continue;
                }
                if(itemstack1.stackSize < itemstack.getMaxStackSize())
                {
                    itemstack.stackSize -= itemstack.getMaxStackSize() - itemstack1.stackSize;
                    itemstack1.stackSize = itemstack.getMaxStackSize();
                    slot.onSlotChanged();
                }
            }

        }
        if(itemstack.stackSize > 0)
        {
            int l = i;
            do
            {
                if(l >= j)
                {
                    break;
                }
                Slot slot1 = (Slot)inventorySlots.get(l);
                ItemStack itemstack2 = slot1.getStack();
                if(itemstack2 == null)
                {
                    slot1.putStack(itemstack.copy());
                    slot1.onSlotChanged();
                    itemstack.stackSize = 0;
                    break;
                }
                l++;
            } while(true);
        }
    }

    public ItemStack func_27086_a(int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(i);
        if(slot != null && slot.func_27006_b())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(i < field_27088_b * 9)
            {
                func_27087_a(itemstack1, field_27088_b * 9, inventorySlots.size());
            } else
            {
                func_27087_a(itemstack1, 0, field_27088_b * 9);
            }
            if(itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    private IInventory field_20137_a;
    private int field_27088_b;
}
