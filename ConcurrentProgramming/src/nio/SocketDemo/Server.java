package nio.SocketDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));
            while(true){
                SocketChannel channel =  serverSocketChannel.accept();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = channel.read(buffer);
                while(read >0){
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        System.out.print((char)buffer.get());
                    }
                    System.out.println();
                    buffer.clear();
                    read = channel.read(buffer);
                }
                if(read == -1){
                    channel.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
