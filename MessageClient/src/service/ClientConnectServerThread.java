package service;

import common.Message;
import common.MessageType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author lty
 * socket通信线程
 */
public class ClientConnectServerThread extends Thread{
    private Socket socket;

    public ClientConnectServerThread(){}

    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run(){
        // Thread需要在后台与服务器持续通信
        while(true){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                // 若服务器没有发生Message对象，线程会阻塞在这里
                Message message = (Message) objectInputStream.readObject();
                String type = message.getMesType();
                if(type.equals(MessageType.MESSAGE_ONLINE_USERS)){
                    String[] onlineUses = message.getContent().split(" ");
                    for (String onlineUs : onlineUses) {
                        System.out.println("用户：" + onlineUs);
                    }
                    System.out.println("输入任意字符以返回主页......");
                } else if(type.equals(MessageType.MESSAGE_CHAT)){
                    System.out.println("\n用户" + message.getSender() + "对你说：" + message.getContent());
                } else if(type.equals(MessageType.MESSAGE_GROUP)){
                    System.out.println("\n用户" + message.getSender() + "对大家说：" + message.getContent());
                } else if(type.equals(MessageType.MESSAGE_FILE)){
                    try (FileOutputStream fileOutputStream = new FileOutputStream(message.getDest())) {
                        fileOutputStream.write(message.getFile());
                    }
                    System.out.println("\n用户：" + message.getSender() + "给你发送了一个文件。文件已存放到：" + message.getDest());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
