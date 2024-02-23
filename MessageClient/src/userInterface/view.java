package userInterface;

import common.User;
import jdk.jshell.execution.Util;
import service.ClientService;
import utils.Utils;

import java.util.Scanner;

/**
 * @author lty
 */
public class view {
    private boolean isLoop = true;
    private String input;
    private ClientService clientService = new ClientService();
    private String userId;
    private String pwd;
    private Scanner scanf = new Scanner(System.in);

    private void mainMenu() throws Exception {
        while(isLoop) {
            System.out.println("===============欢迎登录网络通信系统================");
            System.out.println("\t \t 1.用户注册");
            System.out.println("\t \t 2.登录系统");
            System.out.println("\t \t 9.退出系统");
            System.out.println("================================================");
            System.out.print("请输入您想选择的功能（1或9）：");
            input = Utils.readString(1);
            switch(input){
                case "1":
                    // this.isLoop = false;
                    System.out.print("请输入用户名：");
                    userId = Utils.readString(50);
                    System.out.print("请输入密码：");
                    pwd = Utils.readString(50);
                    if(clientService.checkUser(userId, pwd, "register")){
                        System.out.println(userId + "注册成功！");
                        System.out.println();
                    } else {
                        System.out.println("注册失败！用户名已存在。");
                    }
                    break;
                case "2":
                    // this.isLoop = false;
                    System.out.print("请输入用户名：");
                    userId = Utils.readString(50);
                    System.out.print("请输入密码：");
                    pwd = Utils.readString(50);
                    if(clientService.checkUser(userId, pwd, "login")){
                        System.out.println("===============欢迎（" + userId + "）================");
                        System.out.println();
                        this.secondMenu();
                    } else {
                        System.out.println("登录失败！请确认用户名或密码是否正确。");
                    }
                    break;
                case "9":
                    System.out.println("正在退出系统......");
                    this.isLoop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入。");
                    break;
            }
        }
    }

    private void secondMenu() throws Exception {
        boolean isLoop_1 = true;
        while(isLoop_1) {
            System.out.println("================网络通信系统主界面=================");
            System.out.println("\t \t 1.显示在线用户列表");
            System.out.println("\t \t 2.群发消息");
            System.out.println("\t \t 3.私聊消息");
            System.out.println("\t \t 4.发送文件");
            System.out.println("\t \t 9.退出登录");
            System.out.println("================================================");
            System.out.print("请输入您想选择的功能（1、2、3、4、9）：");
            input = Utils.readString(1);
            switch(input){
                case "1":
                    System.out.println("\n================在线用户列表=================");
                    clientService.onlineUserList();
                    scanf.nextLine();
                    break;
                case "2":
                    System.out.println("请输入你想发送的消息：");
                    String content_group = Utils.readString(500);
                    clientService.groupChat(userId, content_group);
                    break;
                case "3":
                    System.out.print("请输入想聊天的用户名（仅支持在线用户）：");
                    String receiver = Utils.readString(50);
                    System.out.println("请输入你想发送的消息：");
                    String content = Utils.readString(300);
                    clientService.userChat(userId, receiver, content);
                    break;
                case "4":
                    System.out.print("请输入想发送文件的用户号（仅支持在线用户）：");
                    String recId = Utils.readString(50);
                    System.out.println("请输入需要发送的文件的完整路径（比如D:\\xxx.jpg）：");
                    String src = Utils.readString(500);
                    System.out.println("请输入文件存储到对方的完整路径（比如E:\\xxx.jpg）：");
                    String dist = Utils.readString(500);
                    clientService.sendFile(userId, recId, src, dist);
                    break;
                case "9":
                    clientService.logout();
                    isLoop_1 = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
                    break;
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new view().mainMenu();
    }
}
