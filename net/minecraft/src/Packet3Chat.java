package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import org.cmine.main.commands.*;

public class Packet3Chat extends Packet
{
	
	public Commands commands;

    public Packet3Chat()
    {
    }

    public Packet3Chat(String s)
    {
        if(s.length() > 119)
        {
            s = s.substring(0, 119);
        }
        message = s;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        message = func_27037_a(datainputstream, 119);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        func_27038_a(message, dataoutputstream);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleChat(this);
    }

    public int getPacketSize()
    {
        return message.length();
    }

    public String message;
}
