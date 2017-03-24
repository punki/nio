package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


//working but slowwwww
public class SingleThreadedSelectorNonBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));

        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        Map<SocketChannel,Queue<ByteBuffer>> pendingData = new HashMap<>();

        Handler<SelectionKey, IOException> acceptHanderlr = new AcceptHandler(pendingData);
        Handler<SelectionKey, IOException> readHanderlr = new ReadHandler(pendingData);
        Handler<SelectionKey, IOException> writeHanderlr = new WriteHandler(pendingData);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isValid()) {
                    if (selectionKey.isAcceptable()) {
                        System.out.println("Accepted connection");
                        acceptHanderlr.handle(selectionKey);
                    } else if (selectionKey.isReadable()) {
                        System.out.println("Socket ready for reading");
                        readHanderlr.handle(selectionKey);
                    } else if (selectionKey.isWritable()) {
                        System.out.println("Socket ready for writing");
                        writeHanderlr.handle(selectionKey);
                    }
                }
            }


//            SocketChannel newSocket = ssc.accept(); //mostly null, never blocks
//            if (newSocket != null) {
//                sockets.add(newSocket);
//                newSocket.configureBlocking(false);
//                System.out.println("Connected to newSocket = " + newSocket);
//            }else{
////                System.out.println("new socket is null");
//            }
//
//            for (Iterator<SocketChannel> iterator = sockets.iterator(); iterator.hasNext(); ) {
//                SocketChannel sc = iterator.next();
//                if (sc.isConnected()) {
//                    handler.handle(sc);
//                }else{
//                    iterator.remove();
//                }
//            }
        }
    }

}
