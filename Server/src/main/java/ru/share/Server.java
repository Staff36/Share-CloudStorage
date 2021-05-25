package ru.share;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.log4j.Log4j;
import ru.share.Handlers.StringInboundHandler;

@Log4j
public class Server {
    private final int PORT;
    public Server(int PORT) {
        this.PORT = PORT;
        log.debug("Server is starting now");
        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(auth, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new StringInboundHandler());
                    }
                });

            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            log.debug("Server is started");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
           log.error("Something was wrong", e);
        } finally {
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
