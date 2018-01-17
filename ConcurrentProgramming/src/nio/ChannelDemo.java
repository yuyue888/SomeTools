package nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ChannelDemo {
    public static void main(String[] args) {
        FileInputStream file = null;

        try {
            file = new FileInputStream("src/nio/test.txt");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(16);
            int bufferRead = channel.read(buffer);
            while (bufferRead != -1) {
                buffer.flip();
                Charset cs = Charset.forName ("gbk");
                CharBuffer cb = cs.decode (buffer);
                while (cb.hasRemaining()) {
                    System.out.print(cb.get());
                }

                buffer.compact();
                bufferRead = channel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
