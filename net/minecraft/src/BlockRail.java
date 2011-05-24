package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockRail extends Block
{

    public static final boolean func_27029_g(World world, int i, int j, int k)
    {
        int l = world.getBlockId(i, j, k);
        return l == Block.minecartTrack.blockID || l == Block.field_9036_T.blockID || l == Block.field_9034_U.blockID;
    }

    public static final boolean func_27030_c(int i)
    {
        return i == Block.minecartTrack.blockID || i == Block.field_9036_T.blockID || i == Block.field_9034_U.blockID;
    }

    protected BlockRail(int i, int j, boolean flag)
    {
        super(i, j, Material.circuits);
        field_27034_a = flag;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public boolean func_27028_d()
    {
        return field_27034_a;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1)
    {
        setBlockBoundsBasedOnState(world, i, j, k);
        return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        int l = iblockaccess.getBlockMetadata(i, j, k);
        if(l >= 2 && l <= 5)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        } else
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }

    public int func_22009_a(int i, int j)
    {
        if(field_27034_a)
        {
            if(blockID == Block.field_9036_T.blockID && (j & 8) == 0)
            {
                return blockIndexInTexture - 16;
            }
        } else
        if(j >= 6)
        {
            return blockIndexInTexture - 16;
        }
        return blockIndexInTexture;
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockOpaqueCube(i, j - 1, k);
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(!world.singleplayerWorld)
        {
            func_4038_g(world, i, j, k);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(world.singleplayerWorld)
        {
            return;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        int j1 = i1;
        if(field_27034_a)
        {
            j1 &= 7;
        }
        boolean flag = false;
        if(!world.isBlockOpaqueCube(i, j - 1, k))
        {
            flag = true;
        }
        if(j1 == 2 && !world.isBlockOpaqueCube(i + 1, j, k))
        {
            flag = true;
        }
        if(j1 == 3 && !world.isBlockOpaqueCube(i - 1, j, k))
        {
            flag = true;
        }
        if(j1 == 4 && !world.isBlockOpaqueCube(i, j, k - 1))
        {
            flag = true;
        }
        if(j1 == 5 && !world.isBlockOpaqueCube(i, j, k + 1))
        {
            flag = true;
        }
        if(flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        } else
        if(blockID == Block.field_9036_T.blockID)
        {
            boolean flag1 = world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j + 1, k);
            flag1 = flag1 || func_27032_a(world, i, j, k, i1, true, 0) || func_27032_a(world, i, j, k, i1, false, 0);
            boolean flag2 = false;
            if(flag1 && (i1 & 8) == 0)
            {
                world.setBlockMetadataWithNotify(i, j, k, j1 | 8);
                flag2 = true;
            } else
            if(!flag1 && (i1 & 8) != 0)
            {
                world.setBlockMetadataWithNotify(i, j, k, j1);
                flag2 = true;
            }
            if(flag2)
            {
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
                if(j1 == 2 || j1 == 3 || j1 == 4 || j1 == 5)
                {
                    world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
                }
            }
        } else
        if(l > 0 && Block.blocksList[l].canProvidePower() && !field_27034_a && RailLogic.getNAdjacentTracks(new RailLogic(this, world, i, j, k)) == 3)
        {
            func_4038_g(world, i, j, k);
        }
    }

    private void func_4038_g(World world, int i, int j, int k)
    {
        if(world.singleplayerWorld)
        {
            return;
        } else
        {
            (new RailLogic(this, world, i, j, k)).func_596_a(world.isBlockIndirectlyGettingPowered(i, j, k));
            return;
        }
    }

    private boolean func_27032_a(World world, int i, int j, int k, int l, boolean flag, int i1)
    {
        if(i1 >= 8)
        {
            return false;
        }
        int j1 = l & 7;
        boolean flag1 = true;
        switch(j1)
        {
        case 0: // '\0'
            if(flag)
            {
                k++;
            } else
            {
                k--;
            }
            break;

        case 1: // '\001'
            if(flag)
            {
                i--;
            } else
            {
                i++;
            }
            break;

        case 2: // '\002'
            if(flag)
            {
                i--;
            } else
            {
                i++;
                j++;
                flag1 = false;
            }
            j1 = 1;
            break;

        case 3: // '\003'
            if(flag)
            {
                i--;
                j++;
                flag1 = false;
            } else
            {
                i++;
            }
            j1 = 1;
            break;

        case 4: // '\004'
            if(flag)
            {
                k++;
            } else
            {
                k--;
                j++;
                flag1 = false;
            }
            j1 = 0;
            break;

        case 5: // '\005'
            if(flag)
            {
                k++;
                j++;
                flag1 = false;
            } else
            {
                k--;
            }
            j1 = 0;
            break;
        }
        if(func_27031_a(world, i, j, k, flag, i1, j1))
        {
            return true;
        }
        return flag1 && func_27031_a(world, i, j - 1, k, flag, i1, j1);
    }

    private boolean func_27031_a(World world, int i, int j, int k, boolean flag, int l, int i1)
    {
        int j1 = world.getBlockId(i, j, k);
        if(j1 == Block.field_9036_T.blockID)
        {
            int k1 = world.getBlockMetadata(i, j, k);
            int l1 = k1 & 7;
            if(i1 == 1 && (l1 == 0 || l1 == 4 || l1 == 5))
            {
                return false;
            }
            if(i1 == 0 && (l1 == 1 || l1 == 2 || l1 == 3))
            {
                return false;
            }
            if((k1 & 8) != 0)
            {
                if(world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j + 1, k))
                {
                    return true;
                } else
                {
                    return func_27032_a(world, i, j, k, k1, flag, l + 1);
                }
            }
        }
        return false;
    }

    static boolean func_27033_a(BlockRail blockrail)
    {
        return blockrail.field_27034_a;
    }

    private final boolean field_27034_a;
}
