package service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author lty
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;  // 连接到服务端的用户的ID

    ServerConnectClientThread() {}

    ServerConnectClientThread(Socket socket, String userId){
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) input.readObject();
                String type = message.getMesType();
                if(type.equals(MessageType.MESSAGE_ONLINE_USERS)){
                    String onlineUses = ServerDataBase.getOnlineUsers();
                    message.setContent(onlineUses);
                    message.setReceiver(message.getSender());
                    message.setSender("server");
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(message);
                } else if(type.equals(MessageType.MESSAGE_EXIT)){
                    System.out.println(message.getSender() + "退出系统");
                    ServerDataBase.removeUser(message.getSender());
                    socket.close();
                    break;
                } else if(type.equals(MessageType.MESSAGE_CHAT)){
                    // 根据message获取接收到用户的ID，得到对应的线程
                    ServerConnectClientThread receiverThread =
                            ServerDataBase.getClientThread(message.getReceiver());
                    // 得到对应socket的输出流，将message对象发送给指定的客户端
                    new ObjectOutputStream(
                            receiverThread.getSocket().getOutputStream()
                        ).writeObject(message);
                } else if(type.equals(MessageType.MESSAGE_GROUP)){
                    String[] receiveUses = ServerDataBase.getOnlineUsers().split(" ");
                    for (String receiver : receiveUses) {
                        ServerConnectClientThread receiverThread = ServerDataBase.getClientThread(receiver);
                        new ObjectOutputStream(
                                receiverThread.getSocket().getOutputStream()
                            ).writeObject(message);
                    }
                } else if(type.equals(MessageType.MESSAGE_FILE)){
                    ServerConnectClientThread receiverThread =
                            ServerDataBase.getClientThread(message.getReceiver());
                    new ObjectOutputStream(
                            receiverThread.getSocket().getOutputStream()
                        ).writeObject(message);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
