package start;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.EventExecutorGroup;

public class ClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 这个 msg 我们转成一个 ByteBuf  这个东西其实是对 ByteBuffer 的封装
            // 它里面有两个指针，所以说写完再读不需要进行 flip 对指针进行回退
            ByteBuf buf = (ByteBuf) msg;
            byte[] buffer = new byte[buf.readableBytes()];
            buf.readBytes(buffer);
            System.out.println(new String(buffer));
        }finally {
            // 如果说我们没有对 msg 进行写操作我们必须显示的对这个资源进行释放。否则会出现莫名其妙的问题
            // 而当我们写操作了以后 netty 会自动的来完成释放资源的操作
            ReferenceCountUtil.release(msg);
        }
    }
}
