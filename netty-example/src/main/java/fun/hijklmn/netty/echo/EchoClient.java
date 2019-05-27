package fun.hijklmn.netty.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoClient {

	private final String host;
	
	private final int port;
	
	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception {
		
		System.out.println("Client finished £¡");
		
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		
		try {
			
			Bootstrap bootstrap = new Bootstrap();
			
			bootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class).remoteAddress(new InetSocketAddress(host, port)).handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {

					ch.pipeline().addLast(new EchoClientHandler());
					
				}
				
			});
			
			ChannelFuture channelFuture = bootstrap.connect().sync();
			channelFuture.channel().closeFuture().sync();
			
		} finally {

			eventLoopGroup.shutdownGracefully().sync();
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		if (args.length != 2) {
			System.out.println("Usage£º" + EchoClient.class.getSimpleName() + " <host> <port> ");
			return;
		}
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		new EchoClient(host, port).start();
		
	}
	
}
