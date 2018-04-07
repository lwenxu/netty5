package serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                                // .addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                                // .addLast(new SerialiHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8000).sync();
        System.out.println("Client connected ... ");
        future.channel().writeAndFlush(new Request(1, "hello")).addListener(ChannelFutureListener.CLOSE);
        future.channel().closeFuture().sync();
    }
}
