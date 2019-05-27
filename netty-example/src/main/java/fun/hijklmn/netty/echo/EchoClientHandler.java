package fun.hijklmn.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {

		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
		
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		
		System.out.println("Client received£º" + msg.toString(CharsetUtil.UTF_8));
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		cause.printStackTrace();
		
		ctx.close();
		
	}
	
}
