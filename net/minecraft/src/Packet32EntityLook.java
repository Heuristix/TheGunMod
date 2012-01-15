package net.minecraft.src;

import java.io.*;

public class Packet32EntityLook extends Packet30Entity
{
    public Packet32EntityLook()
    {
        rotating = true;
    }

    public Packet32EntityLook(int i, byte byte0, byte byte1)
    {
        super(i);
        yaw = byte0;
        pitch = byte1;
        rotating = true;
    }

    public void readPacketData(DataInputStream datainputstream)
    throws IOException
    {
        super.readPacketData(datainputstream);
        yaw = datainputstream.readByte();
        pitch = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream)
    throws IOException
    {
        super.writePacketData(dataoutputstream);
        dataoutputstream.writeByte(yaw);
        dataoutputstream.writeByte(pitch);
    }

    public int getPacketSize()
    {
        return 6;
    }
}
