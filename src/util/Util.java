package util;

import java.nio.ByteBuffer;

public class Util {
    public static int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }

    public static void transmogrify(ByteBuffer buffer) {
        buffer.flip();//== buffer.limit(buffer.position()).position(0);
        for (int i = 0; i < buffer.limit(); i++) {
            buffer.put(i, (byte) transmogrify(buffer.get(i)));
        }
    }
}
