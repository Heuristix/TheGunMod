package net.minecraft.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import net.minecraft.server.MinecraftServer;

class NetworkAcceptThread extends Thread
{
    final MinecraftServer mcServer;
    final NetworkListenThread netWorkListener;

    NetworkAcceptThread(NetworkListenThread networklistenthread, String s, MinecraftServer minecraftserver)
    {
        super(s);
        netWorkListener = networklistenthread;
        mcServer = minecraftserver;
    }

    public void run()
    {
        do
        {
            if (!netWorkListener.isListening)
            {
                break;
            }
            try
            {
                Socket socket = NetworkListenThread.getServerSocket(netWorkListener).accept();
                if (socket == null)
                {
                    continue;
                }
                synchronized (NetworkListenThread.func_35504_b(netWorkListener))
                {
                    java.net.InetAddress inetaddress = socket.getInetAddress();
                    if (NetworkListenThread.func_35504_b(netWorkListener).containsKey(inetaddress) && System.currentTimeMillis() - ((Long)NetworkListenThread.func_35504_b(netWorkListener).get(inetaddress)).longValue() < 5000L)
                    {
                        NetworkListenThread.func_35504_b(netWorkListener).put(inetaddress, Long.valueOf(System.currentTimeMillis()));
                        socket.close();
                        continue;
                    }
                    NetworkListenThread.func_35504_b(netWorkListener).put(inetaddress, Long.valueOf(System.currentTimeMillis()));
                }
                NetLoginHandler netloginhandler = new NetLoginHandler(mcServer, socket, (new StringBuilder()).append("Connection #").append(NetworkListenThread.func_712_b(netWorkListener)).toString());
                NetworkListenThread.func_716_a(netWorkListener, netloginhandler);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
        while (true);
    }
}
