package server;

import util.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TransmogrifyChanelHandler implements Handler<SocketChannel, IOException> {

    @Override
    public void handle(SocketChannel socket) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(80);
        int read = socket.read(buffer);
        if (read == -1) {
            socket.close();
            return;
        }
        if (read > 0) {
            Util.transmogrify(buffer);
            while (buffer.hasRemaining()) {
                socket.write(buffer);
            }
        }
    }
}
