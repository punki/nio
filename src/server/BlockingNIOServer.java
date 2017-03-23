package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingNIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));


        ExecutorService pool = new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000)); // extra work will wait in the queue (but unbounded so it can crash)
        Handler<SocketChannel, IOException> handler = new ExecutorServiceHandler<>(pool,new ExceptionHandler<>(
                new PrintingHandler<>(new BlockingChanelHandler(new TransmogrifyChanelHandler())),
                throwable -> System.out.println("throwable = " + throwable)
        ));
        while (true) {
            SocketChannel sc = ssc.accept(); //blocks
            handler.handle(sc);

        }
    }

}
