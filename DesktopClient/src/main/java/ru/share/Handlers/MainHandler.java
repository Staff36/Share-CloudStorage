package ru.share.Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class MainHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("Connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //NULL
            if (msg == null) {
                return;
            }
            //String
            if (msg instanceof String){
                System.out.println(msg);
            }
            //File
            if (msg instanceof File){
                File file = (File) msg;
                if (file.isDirectory()){
                    Arrays.stream(Objects.requireNonNull(file.listFiles())).map(x -> {
                        StringBuilder sb = new StringBuilder();
                        if (x.isDirectory()){
                            sb.append("DIRE: ");
                        } else {
                            sb.append("FILE: ");
                        }
                        sb.append(x.getName());
                        return sb.toString();
                    }).forEach(System.out::println);
                } else {
                    System.out.println("Single File" + file.getName());
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
