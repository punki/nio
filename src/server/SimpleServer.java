package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Only one connection at the time is working.
 * Make 2 telnet connections and only one will get answers
 */
public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        Handler<Socket, IOException> handler = new TransmogrifyHandler();
        while (true) {
            Socket s = ss.accept();
            handler.handle(s);
            s.close();
        }
    }

}
