package fun.hijklmn.netty.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * echo�����
 * @author ASUS
 *
 */
public class EchoServer {

	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public void start() throws Exception {
		
		System.out.println("Server finished !");
		
		final EchoServerHanlder echoServerHanlder = new EchoServerHanlder();
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port)).childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					
					ch.pipeline().addLast(echoServerHanlder);
					
				}
			});
			
			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			
			channelFuture.channel().closeFuture().sync();
			
		} finally {
			
			group.shutdownGracefully().sync();
			
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		
		if (args.length != 1) {
			System.out.println("Usage ��" + EchoServer.class.getSimpleName() + " <port>");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		new EchoServer(port).start();
		
	}
	
}
