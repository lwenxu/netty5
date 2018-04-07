package file;

import heartBeat.IdleStateHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        EventLoopGroup boss = new NioEventLoopGroup();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                // 客户端和服务端的任务队列数之和，超过这个值就拒绝服务
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 1024)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChunkedWriteHandlerInitializer());

        ChannelFuture future = bootstrap.bind(8000).sync();
        System.out.println("Server start ... ");
        future.channel().closeFuture().sync();
    }
}
