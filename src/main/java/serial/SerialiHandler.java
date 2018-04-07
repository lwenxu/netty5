package serial;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

public class SerialiHandler extends ChannelHandlerAdapter{
            // @Override
            // public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //     ctx.writeAndFlush(new Request(1, "haha")).addListener(ChannelFutureListener.CLOSE);
            // }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println(msg.toString());
    }
}
