package service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lty
 * 服务器端，监听9999号端口，等待客户端连接并保持通信
 */
public class ServerService {
    private ServerSocket serverSocket;

    public ServerService() throws Exception {
        System.out.println("服务器在9999端口监听...");
        try {
            serverSocket = new ServerSocket(9999);
            while(true){    // 当某个用户连接后，会继续侦听
                Socket socket = serverSocket.accept();
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                String content = (String) input.readObject();
                User user = (User) input.readObject();
                Message message = new Message();
                if(content.equals("register")){
                    if(ServerDataBase.checkUser(user.getUserId(), user.getPassWord())){
                        message.setMesType(MessageType.MESSAGE_REGISTER_SUCCEED);
                    } else {
                        message.setMesType(MessageType.MESSAGE_REGISTER_FAIL);
                    }
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(message);
                    socket.close();
                } else {
                    if (ServerDataBase.getUser(user.getUserId(), user.getPassWord())) {
                        message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                        ServerConnectClientThread serverThread = new ServerConnectClientThread(socket, user.getUserId());
                        serverThread.start();
                        ServerDataBase.addServerThread(user.getUserId(), serverThread);
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(message);
                    } else {
                        message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(message);
                        socket.close();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            serverSocket.close();
        }
    }

    public ServerService(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws Exception {
        new ServerService();
    }
}
