package file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedStream;

import java.io.File;
import java.io.FileInputStream;

public class WriteStreamHandler extends SimpleChannelInboundHandler<Object>{

    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        File file = new File("/Users/lwen/IdeaProjects/JavaCode/Netty/src/main/java/file/Server.java");
        ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
    }
}
