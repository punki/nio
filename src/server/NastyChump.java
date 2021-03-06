package server;

import java.io.IOException;
import java.net.Socket;

public class NastyChump {
    public static void main(String[] args) throws IOException {
        Socket[] sockets = new Socket[1];
        for (int i = 0; i < sockets.length; i++) {
            try {
                //Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
                sockets[i] = new Socket("localhost", 8080);
                System.out.println("i = " + i);
            } catch (IOException e) {
                System.out.println("Error = " + e);
            }
        }
        for (Socket socket : sockets) {
            socket.close();
        }
    }
}
