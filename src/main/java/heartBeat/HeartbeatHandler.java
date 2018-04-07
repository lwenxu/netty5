package heartBeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class HeartbeatHandler extends SimpleChannelInboundHandler {
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("you will be closed ... ", CharsetUtil.ISO_8859_1));  //2

    /**
     * 这个是一个触发器方法，也就是如果说我们的事件是属于 Idle 事件我们就触发
     * 否则使用默认的触发方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE);  //3
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }
}