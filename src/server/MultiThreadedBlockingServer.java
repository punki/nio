package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        Handler<Socket, IOException> handler = new ThreadedHandler<>(new ExceptionHandler<>(
                new PrintingHandler<>(new TransmogrifyHandler()),
                throwable -> System.out.println("throwable = " + throwable)
        ));
        while (true) {
            Socket s = ss.accept();
            handler.handle(s);

        }
    }

}
