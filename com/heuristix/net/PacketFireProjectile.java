package com.heuristix.net;

import com.heuristix.Util;
import net.minecraft.src.Packet230ModLoader;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 1/9/12
 * Time: 9:22 PM
 */
public class PacketFireProjectile extends Packet230ModLoader {

    public static final int PACKET_ID = 231;
    static {
        Util.setPacketId(PacketFireProjectile.class, PACKET_ID, true, true);
    }

    public PacketFireProjectile() {
        super();
        this.packetType = 0;
    }
}
