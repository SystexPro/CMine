package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class StatBase
{

    public StatBase(int i, String s, IStatType istattype)
    {
        field_27058_g = false;
        field_25063_d = i;
        field_25062_e = s;
        field_25065_a = istattype;
    }

    public StatBase(int i, String s)
    {
        this(i, s, field_27056_i);
    }

    public StatBase func_27052_e()
    {
        field_27058_g = true;
        return this;
    }

    public StatBase func_27053_d()
    {
        if(StatList.field_25104_C.containsKey(Integer.valueOf(field_25063_d)))
        {
            throw new RuntimeException((new StringBuilder()).append("Duplicate stat id: \"").append(((StatBase)StatList.field_25104_C.get(Integer.valueOf(field_25063_d))).field_25062_e).append("\" and \"").append(field_25062_e).append("\" at id ").append(field_25063_d).toString());
        } else
        {
            StatList.field_25123_a.add(this);
            StatList.field_25104_C.put(Integer.valueOf(field_25063_d), this);
            field_27057_h = AchievementMap.func_25132_a(field_25063_d);
            return this;
        }
    }

    public String toString()
    {
        return field_25062_e;
    }

    public final int field_25063_d;
    public final String field_25062_e;
    public boolean field_27058_g;
    public String field_27057_h;
    private final IStatType field_25065_a;
    private static NumberFormat field_25066_b;
    public static IStatType field_27056_i = new StatTypeSimple();
    private static DecimalFormat field_25068_c = new DecimalFormat("########0.00");
    public static IStatType field_27055_j = new StatTypeTime();
    public static IStatType field_27054_k = new StatTypeDistance();

    static 
    {
        field_25066_b = NumberFormat.getIntegerInstance(Locale.US);
    }
}
