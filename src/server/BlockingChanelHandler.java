package server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class BlockingChanelHandler implements Handler<SocketChannel, IOException> {

    private final Handler<SocketChannel, IOException> next;

    public BlockingChanelHandler(Handler<SocketChannel, IOException> next) {
        this.next = next;
    }

    @Override
    public void handle(SocketChannel sc) throws IOException {
        while (sc.isConnected()) {
            next.handle(sc);
        }
    }
}
