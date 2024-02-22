package service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author lty
 * 用户注册与登录功能
 */
public class ClientService {
    private User user = new User();
    private Message init = new Message();
    private Socket socket;

    // 根据id和pwd去服务器端验证该用户是否合法
    public boolean checkUser(String userId, String pwd, String content) throws Exception {
        init.setContent(content);
        user.setUserId(userId);
        user.setPassWord(pwd);
        socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(init);
        objectOutputStream.writeObject(user);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) objectInputStream.readObject();
        if(message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
            ClientConnectServerThread clientThread = new ClientConnectServerThread(socket);
            clientThread.start();
            ManageClientThread.addClientThread(user.getUserId(), clientThread);     // 把线程放入线程池
            return true;
        } else if(message.getMesType().equals(MessageType.MESSAGE_REGISTER_SUCCEED)){
            return true;
        } else if(message.getMesType().equals(MessageType.MESSAGE_LOGIN_FAIL)
                || message.getMesType().equals(MessageType.MESSAGE_REGISTER_FAIL)){
            socket.close();
        }
        return false;
    }

    public static void main(String[] args) {
        new ClientService();
    }
}
