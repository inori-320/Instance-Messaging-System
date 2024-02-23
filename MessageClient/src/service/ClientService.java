package service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author lty
 * 用户注册与登录功能
 */
public class ClientService {
    private User user = new User();
    private Socket socket;

    // 根据id和pwd去服务器端验证该用户是否合法
    public boolean checkUser(String userId, String pwd, String content) throws Exception {
        user.setUserId(userId);
        user.setPassWord(pwd);
        socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(content);
        objectOutputStream.writeObject(user);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) objectInputStream.readObject();
        String type = message.getMesType();
        if(type.equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
            ClientConnectServerThread clientThread = new ClientConnectServerThread(socket);
            clientThread.start();
            ClientDataBase.addClientThread(user.getUserId(), clientThread);     // 把线程放入线程池
            return true;
        } else if(type.equals(MessageType.MESSAGE_REGISTER_SUCCEED)){
            return true;
        } else if(type.equals(MessageType.MESSAGE_LOGIN_FAIL)
                || message.getMesType().equals(MessageType.MESSAGE_REGISTER_FAIL)){
            socket.close();
        }
        return false;
    }

    public void onlineUserList() throws Exception {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_ONLINE_USERS);
        message.setSender(user.getUserId());
        new ObjectOutputStream(ClientDataBase.getClientThread(
                user.getUserId()).getSocket().getOutputStream()
        ).writeObject(message);
    }

    public void logout() throws Exception {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_EXIT);
        message.setSender(user.getUserId());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(message);
        System.out.println(user.getUserId() + "退出系统");
        System.exit(0); // 退出进程
    }

    public void userChat(String sender, String receiver, String content) throws IOException {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTime(new java.util.Date().toString());
        message.setMesType(MessageType.MESSAGE_CHAT);
        new ObjectOutputStream(ClientDataBase.getClientThread(
                user.getUserId()).getSocket().getOutputStream()
        ).writeObject(message);
    }

    public void groupChat(String sender, String content) throws IOException {
        Message message = new Message();
        message.setSender(sender);
        message.setContent(content);
        message.setTime(new java.util.Date().toString());
        message.setMesType(MessageType.MESSAGE_GROUP);
        new ObjectOutputStream(ClientDataBase.getClientThread(
                user.getUserId()).getSocket().getOutputStream()
        ).writeObject(message);
    }

    public void sendFile(String sender, String receiver, String src, String dist) throws IOException {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setTime(new java.util.Date().toString());
        message.setMesType(MessageType.MESSAGE_FILE);
        message.setSrc(src);
        message.setDest(dist);

        byte[] fileBytes = new byte[(int)new File(src).length()];
        try (FileInputStream fileInputStream = new FileInputStream(src)) {
            fileInputStream.read(fileBytes);
            message.setFile(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("给用户" + receiver + "发送文件成功");
        new ObjectOutputStream(
                ClientDataBase.getClientThread(sender).getSocket().getOutputStream())
                .writeObject(message);
    }
}
