package ru.share.Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j;
import ru.share.MessageTypes.RegularFile;

import java.io.File;
@Log4j
public class MainMessageHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null){
            return;
        }
        if (msg instanceof String){
            ctx.writeAndFlush("Server say: " + msg);
            if (msg.toString().startsWith("list")){
                ctx.writeAndFlush(new File("C:\\GeekBrains\\JavaCloudStorage\\Share"));
            }

        }
        if (msg instanceof RegularFile){
            log.info("Catch file");
            RegularFile file = (RegularFile) msg;
            file.writeInstanceToFile(new File("C:\\GeekBrains\\JavaCloudStorage\\Share\\dest.txt"));
            log.info("File written");
        }

    }
}
