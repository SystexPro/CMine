package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class SlotFurnace extends Slot
{

    public SlotFurnace(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
        field_27007_d = entityplayer;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }

    public boolean func_25003_d()
    {
        return true;
    }

    public void onPickupFromSlot(ItemStack itemstack)
    {
        if(itemstack.itemID == Item.ingotIron.shiftedIndex)
        {
            field_27007_d.func_25046_a(AchievementList.field_27108_k, 1);
        }
        if(itemstack.itemID == Item.fishCooked.shiftedIndex)
        {
            field_27007_d.func_25046_a(AchievementList.field_27103_p, 1);
        }
        super.onPickupFromSlot(itemstack);
    }

    private EntityPlayer field_27007_d;
}
