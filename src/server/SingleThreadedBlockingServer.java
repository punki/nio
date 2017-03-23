package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Only one connection at the time is working.
 * Make 2 telnet connections and only one will get answers
 */
public class SingleThreadedBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        Handler<Socket, IOException> handler = new ExceptionHandler<>(
                new PrintingHandler<>(new TransmogrifyHandler()),
                throwable -> System.out.println("throwable = " + throwable)
        );
        while (true) {
            Socket s = ss.accept();
            handler.handle(s);
            s.close();
        }
    }

}
