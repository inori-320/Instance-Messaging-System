package service;

import common.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lty
 * 管理客户端连接服务器端的线程，简易版线程池，使用哈希表实现
 */
public class ServerDataBase {
    private static final Map<String, ServerConnectClientThread> threadData = new ConcurrentHashMap<>();
    private static final Map<String, User> usersData = new ConcurrentHashMap<>();

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

    // 判断用户是否登录成功
    public static boolean getUser(String userId, String pwd){
        if(usersData.get(userId) != null){
            return usersData.get(userId).getPassWord().equals(pwd);
        }
        return false;
    }

    // 检查用户是否在数据库中
    public static boolean checkUser(String userId, String pwd){
        if(pwd.isEmpty()){
            return usersData.get(userId) != null;
        } else {
            if(usersData.get(userId) == null){
                addUser(userId, pwd);
                return true;
            }
            return false;
        }
    }

    // 获取在线用户列表到一个字符串中
    public static String getOnlineUsers(){
        StringBuilder s = new StringBuilder();
        for(String keys: usersData.keySet()){
            s.append(keys).append(" ");
        }
        return s.toString().trim();
    }

    // 用户下线，从集合中删除线程对象
    public static void removeUser(String userId){
        threadData.remove(userId);
    }
}
