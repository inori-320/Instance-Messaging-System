package service;

import common.Message;
import common.MessageType;
import utils.Utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @author lty
 */
public class SendNews implements Runnable{
    @Override
    public void run() {
        while(true) {
            System.out.println("请输入服务器需要推送的新闻（输入exit退出新闻推送功能）：");
            String news = Utils.readString(1000);
            if(news.equals("exit")) {
                System.out.println("退出新闻推送。");
                break;
            }
            Message message = new Message();
            message.setSender("server");
            message.setMesType(MessageType.MESSAGE_GROUP);
            message.setTime(new java.util.Date().toString());
            message.setContent(news);
            System.out.println("服务器已推送该消息");

            String[] users = ServerDataBase.getOnlineUsers().split(" ");
            for (String userId : users) {
                try {
                    new ObjectOutputStream(
                            ServerDataBase.getClientThread(userId).getSocket().getOutputStream()
                    ).writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
