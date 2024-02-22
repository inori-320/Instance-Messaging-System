package service;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
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
            System.out.println("服务器与客户端进行通信中......");
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
