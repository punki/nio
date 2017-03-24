package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


//working but slowwwww
public class SingleThreadedPollingNonBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));

        ssc.configureBlocking(false);

        Handler<SocketChannel, IOException> handler = new TransmogrifyChanelHandler();
        Collection<SocketChannel> sockets = new ArrayList<>();
        while (true) {
            SocketChannel newSocket = ssc.accept(); //mostly null, never blocks
            if (newSocket != null) {
                sockets.add(newSocket);
                newSocket.configureBlocking(false);
                System.out.println("Connected to newSocket = " + newSocket);
            }else{
//                System.out.println("new socket is null");
            }

            for (Iterator<SocketChannel> iterator = sockets.iterator(); iterator.hasNext(); ) {
                SocketChannel sc = iterator.next();
                if (sc.isConnected()) {
                    handler.handle(sc);
                }else{
                    iterator.remove();
                }
            }
        }
    }

}
