package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class EchoHandler extends SimpleChannelInboundHandler<HttpObject> {

    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpObject o) throws Exception {
        if(o instanceof HttpRequest) {
            HttpRequest msgs = (HttpRequest) o;
            System.out.println("接收到请求：  "+msgs.method());
            //设置返回内容
            ByteBuf content = Unpooled.copiedBuffer("Hello World\n", CharsetUtil.UTF_8);
            //创建响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes() + "");
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
