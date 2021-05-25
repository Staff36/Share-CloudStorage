package ru.share.Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        FileListMessage fileListMessage = new FileListMessage(getFilesList());
        ctx.writeAndFlush(fileListMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null) {
                return;
            }

            if (msg instanceof FileListMessage) {
                FileListMessage fileListMessage = new FileListMessage(getFilesList());
                ctx.writeAndFlush(fileListMessage);
            }
            if (msg instanceof FileRequest) {
                FileRequest fileRequest = (FileRequest) msg;
                if (Files.exists(Paths.get("C:\\test" + fileRequest.getFileName()))) {
                    Files.delete(Paths.get("C:\\test" + fileRequest.getFileName()));
                }
                FileListMessage fileListMessage = new FileListMessage(getFilesList());
                ctx.writeAndFlush(fileListMessage);
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

    private List<String> getFilesList() throws IOException {
        List<String> files = new ArrayList<>();
        Files.newDirectoryStream(Paths.get("C:\\test")).forEach(x -> files.add(x.getFileName().toString()));
        return files;
    }
}
