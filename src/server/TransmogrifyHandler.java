package server;

import util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TransmogrifyHandler implements Handler<Socket, IOException> {

    @Override
    public void handle(Socket socket) throws IOException {
        try (
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()) {
            int data;
            while ((data = in.read()) != -1) {
                out.write(Util.transmogrify(data));
            }
        }
    }
}
