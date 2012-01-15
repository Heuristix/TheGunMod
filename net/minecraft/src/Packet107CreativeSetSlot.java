package net.minecraft.src;

import java.io.*;

public class Packet107CreativeSetSlot extends Packet
{
    public int slot;
    public ItemStack itemStack;

    public Packet107CreativeSetSlot()
    {
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleCreativeSetSlot(this);
    }

    public void readPacketData(DataInputStream datainputstream)
    throws IOException
    {
        slot = datainputstream.readShort();
        itemStack = func_40262_b(datainputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream)
    throws IOException
    {
        dataoutputstream.writeShort(slot);
        writeItemStack(itemStack, dataoutputstream);
    }

    public int getPacketSize()
    {
        return 8;
    }
}
