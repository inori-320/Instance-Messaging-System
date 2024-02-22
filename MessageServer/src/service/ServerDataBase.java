package service;

import common.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lty
 * 管理客户端连接服务器端的类，简易版线程池，使用哈希表实现
 */
public class ServerDataBase {
    private static Map<String, ServerConnectClientThread> threadData = new ConcurrentHashMap<>();
    private static Map<String, User> usersData = new ConcurrentHashMap<>();

    public static void addServerThread(String userId, ServerConnectClientThread serverThread){
        threadData.put(userId, serverThread);
    }

    public static ServerConnectClientThread getClientThread(String userId){
        return threadData.get(userId);
    }

    public static void addUser(String userId, String pwd){
        User user = new User(userId, pwd);
        usersData.put(userId, user);
    }

    public static boolean getUser(String userId, String pwd){
        if(usersData.get(userId) != null){
            return usersData.get(userId).getPassWord().equals(pwd);
        }
        return false;
    }

    public static boolean checkUser(String userId, String pwd){
        if(usersData.get(userId) == null){
            addUser(userId, pwd);
            return true;
        }
        return false;
    }
}
