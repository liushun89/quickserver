package com.shun.liu.quickserver.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Created by liushun on 17/1/13.
 */
public class SimpleHttpServer {
    public static void main(String[] args) {
        int port = 60000;
        boolean enableSSL = true;
        NioEventLoopGroup boss = new NioEventLoopGroup(2);
        NioEventLoopGroup worker = new NioEventLoopGroup(8);
        try {
            final SslContext sslCtx;
            if(enableSSL){
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            }else{
                sslCtx = null;
            }

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleHttpServerInitializer(sslCtx));
            Channel channel = serverBootstrap.bind(port).sync().channel();
            System.err.println("Open your web browser and navigate to " +
                    ("http") + "://127.0.0.1:" + port + '/');
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
