package UserInterface;

import common.Users;
import utils.Utils;

import java.util.Scanner;

/**
 * @author lty
 */
public class view {
    private boolean isLoop = true;
    private String input;
    Users user = new Users();

    private void mainMenu(){
        while(isLoop) {
            System.out.println("===============欢迎登录网络通信系统================");
            System.out.println("\t \t 1.登录系统");
            System.out.println("\t \t 9.退出系统");
            System.out.println("================================================");
            System.out.print("请输入您想选择的功能（1或9）：");
            input = Utils.readString(1);
            switch(input){
                case "1":
                    // this.isLoop = false;
                    System.out.print("请输入用户名：");
                    user.setUserId(Utils.readString(50));
                    System.out.print("请输入密码：");
                    user.setPassWord(Utils.readString(50));
                    if(true){
                        System.out.println("===============欢迎（" + user.getUserId() + "）================");
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

    private void secondMenu(){
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
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "9":
                    System.out.println("退出登录。");
                    isLoop_1 = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new view().mainMenu();
    }
}
