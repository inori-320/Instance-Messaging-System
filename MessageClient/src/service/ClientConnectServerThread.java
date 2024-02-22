package service;

import common.Message;

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
            System.out.println("客户端线程——等待读取服务器端发送的数据");
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                // 若服务器没有发生Message对象，线程会阻塞在这里
                Message message = (Message) objectInputStream.readObject();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
