package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        ExecutorService pool = new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000)); // extra work will wait in the queue (but unbounded so it can crash)
        Handler<Socket, IOException> handler = new ExecutorServiceHandler<>(pool,new ExceptionHandler<>(
                new PrintingHandler<>(new TransmogrifyHandler()),
                throwable -> System.out.println("throwable = " + throwable)
        ));
        while (true) {
            Socket s = ss.accept();
            handler.handle(s);

        }
    }

}
