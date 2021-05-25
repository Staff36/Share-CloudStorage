package ru.share;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j;

import java.nio.ByteBuffer;

@Log4j
public class InboundByteHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Client connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Client disconnected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception in inbound handler "  , cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        StringBuilder stringBuilder = new StringBuilder("byteBuf1: ");
        while (byteBuf.isReadable()){
            System.out.println(byteBuf.capacity());
            stringBuilder.append((char) byteBuf.readByte());
        }
        log.info(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
          }
}
