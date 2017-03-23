package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        ExecutorService pool = Executors.newFixedThreadPool(10);
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
