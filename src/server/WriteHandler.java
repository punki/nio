package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

public class WriteHandler implements Handler<SelectionKey, IOException> {
    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public WriteHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        Queue<ByteBuffer> byteBuffers = pendingData.get(sc);
        while (!byteBuffers.isEmpty()) {
            ByteBuffer buffer = byteBuffers.peek();
            int write = sc.write(buffer);
            if (write==-1) {
                sc.close();
                pendingData.remove(sc);
                return;
            }
            if (buffer.hasRemaining()) {
                return;
            }else {
                byteBuffers.remove();
            }
        }
        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
