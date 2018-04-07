package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

public class HttpHandler extends ChannelInitializer<SocketChannel> {
    boolean client;
    public HttpHandler(boolean b) {
        client = b;
    }


    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

            pipeline
                    .addLast(new HttpServerCodec())
                    .addLast("aggegator", new HttpObjectAggregator(512 * 1024))
                    .addLast(new EchoHandler());
    }
}
