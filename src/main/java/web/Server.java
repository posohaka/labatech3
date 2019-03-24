package web;

import web.util.Converter;
import web.util.Messege;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket firstclient;
    private static Socket secondclient;
    private static InputStream inFirst;
    private static OutputStream outFirst;
    private static InputStream inSecond;
    private static OutputStream outSecond;

    private static final int port = 4004;

    public static void main(String[] args) {
        try {
            try {
                serverSocket = new ServerSocket(port, 2);
                System.out.println("Сервер запущен");
                while (true) {
                    try {
                        firstclient = serverSocket.accept();
                        System.out.println("Первый игрок вошел в игру");
                        inFirst = firstclient.getInputStream();
                        outFirst = firstclient.getOutputStream();
                        Converter.toJSON(outFirst, new Messege("1"));
                        outFirst.flush();

                        System.out.println("lol");

                        secondclient = serverSocket.accept();

                        if(firstclient.isClosed()){
                            System.out.println("pizda");
                        }

                        System.out.println("Второй игрок вошел в игру");
                        inSecond = secondclient.getInputStream();
                        outSecond = secondclient.getOutputStream();
                        Converter.toJSON(outSecond, new Messege("2"));
                        outSecond.flush();
                        while (true) {
                            Messege messege = Converter.toMessege(inSecond);
                            Converter.toJSON(outFirst, messege);
                            outFirst.flush();
                            if(messege.getMess().trim().equals("q")){
                                System.out.println("second out");
                                break;
                            }
                            Messege messege1 = Converter.toMessege(inFirst);
                            Converter.toJSON(outSecond, messege1);
                            if(messege1.getMess().trim().equals("q")){
                                System.out.println("first out");
                                break;
                            }
                            if (messege1.getMess().equals("=")) {
                                System.out.println("Игра окончена");
                                break;
                            }
                        }

                    } finally {
                        firstclient.close();
                        inFirst.close();
                        outFirst.close();

                        secondclient.close();
                        inSecond.close();
                        outSecond.close();
                        System.out.println("finally");
                    }
                }
            } catch (IOException e) {
                System.out.println("Произошла ошибка");
                e.printStackTrace();
            } finally {
                System.out.println("Сервер закрыт");
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
