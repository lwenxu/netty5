package bindstone.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


public class Client {
    public static void main(String[] args) throws InterruptedException {
        // 名字不同于 Server
        Bootstrap bootstrap = new Bootstrap();
        // 只有读写操作所以我们只需要一个线程池就可以了，而在 Server 端是一个线程池监听事件
        // 一个用来读写数据
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker)
                // 这点不同于 Server 端就是名字有一点点差别
                .channel(NioSocketChannel.class)
                // 这都是一样的，就是设置一堆的 handler
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("&&".getBytes())))
                                .addLast(new StringDecoder())
                                .addLast(new ClientHandler());
                                // 采用固定长度方式分包
                                // .addLast(new FixedLengthFrameDecoder(5));
                    }
                });

        ChannelFuture future = bootstrap.connect("127.0.0.1", 8000).sync();
        System.out.println("Client connected ... ");
        // 这里连续的写会导致粘包和拆包的现象  也就是服务端看到的包的数量少于 5
        for (int i = 0; i < 5; i++) {
            future.channel().writeAndFlush(Unpooled.copiedBuffer("hello server.....&&".getBytes()));
        }
        // 异步的阻塞
        future.channel().closeFuture().sync();
    }
}
