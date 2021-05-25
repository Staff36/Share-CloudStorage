package ru.share.Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j;
import ru.share.DAO.UserDAO;
import ru.share.DAO.UserDAOImplMySQL;
import ru.share.Data.User;

@Log4j
public class StringInboundHandler extends SimpleChannelInboundHandler<String> {
    UserDAO userDAO =  new UserDAOImplMySQL();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if (s.startsWith("/login")){
            String[] strings = s.split("\\s");
            if (strings.length == 3){
                User user = userDAO.getUserByLogin(strings[1]);
                    if (user.getEMail() != null){
                        if (user.getEMail().equals(strings[1]) && user.getPassword().equals(strings[2])){
                            if (user.isAuthorized()){
                                channelHandlerContext.writeAndFlush("Success");
                            } else {
                                channelHandlerContext.writeAndFlush("Confirm tour e-mail address");
                            }
                        } else {
                            channelHandlerContext.writeAndFlush("Incorrect password");
                        }
                    } else {
                        channelHandlerContext.writeAndFlush("User not found");
                    }

            } else {
                channelHandlerContext.writeAndFlush("incorrect e-mail or password");
            }

        } else {
            channelHandlerContext.writeAndFlush("Server ask  " + s);
        }

    }
}
