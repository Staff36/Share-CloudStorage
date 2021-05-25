package ru.share.Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;

public class FileInboundHandler extends SimpleChannelInboundHandler<File> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, File file) throws Exception {
        System.out.println(file);
    }
}
