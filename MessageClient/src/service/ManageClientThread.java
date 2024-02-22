package service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lty
 * 管理客户端连接服务器端的类，简易版线程池，使用哈希表实现
 */
public class ManageClientThread {
    private static Map<String, ClientConnectServerThread> map = new HashMap<>();

    public static void addClientThread(String userId, ClientConnectServerThread clientThread){
        map.put(userId, clientThread);
    }

    public static ClientConnectServerThread getClientThread(String userId){
        return map.get(userId);
    }
}
