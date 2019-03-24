package web;

import com.sun.xml.internal.ws.api.message.Message;
import web.util.Converter;
import web.util.Messege;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket clent;
    private static InputStream in;
    private static OutputStream out;
    private static final int port = 4004;
    private static final String ADRESS = "localhost";

    public static void main(String[] args) {
        try {
            try {
                clent = new Socket(ADRESS, port);
                System.out.println("Клиент запущен");
                in = clent.getInputStream();
                out = clent.getOutputStream();
                Messege messege = Converter.toMessege(in);

                if (messege.getMess().equals("1")) {
                    playerOne();
                }
                else {
                    playerTwo(in, out);
                }
            }
            finally {
                clent.close();
                System.out.println("Клиент закрыт");
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void playerOne() throws IOException {

        System.out.println("Вы зашали первым. Значит и число вам загадывать.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Messege str = Converter.toMessege(in);
            if(str.getMess().equals("q")){
                System.out.println("Ваш оппонент отключился");
                break;
            }
            System.out.println("Второй игрок ввел число " + str.getMess());
            System.out.println("Если ваше число больше введите > . Если ваше число меньше введите < . Если это ваше число, введите = .");
            boolean flag = true;
            String tmp = new String();
            while (flag) {
                 tmp = scanner.nextLine();
                 tmp = tmp.trim();
                if (!tmp.equals("<") && !tmp.equals(">") && !tmp.equals("=") && !tmp.equals("q")) {
                    System.out.println("У вас некорректный ввод. Попробуйте еще раз.");
                }
                else {
                    flag = false;
                }
            }
            if(tmp.equals("q")){
                break;
            }
            Converter.toJSON(out, new Messege(tmp));
            out.flush();
            if (tmp.equals("=")) {
                System.out.println("Ваше число угадали. Игра окончена.");
                break;
            }
        }
    }

    private static void playerTwo(InputStream in, OutputStream out) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите целое число, которое загадал первый игрок.");
            boolean flag = true;
            String tmp = new String();
            int a = 0;
            String finalres = "";
            while (flag) {
                try {
                    tmp = scanner.nextLine();
                    if(tmp.trim().equals("q")){
                        finalres = tmp;
                        flag = false;
                    }else {
                        a = Integer.parseInt(tmp.trim());
                        finalres = String.valueOf(a);
                        flag = false;
                    }
                }
                catch (Exception e) {
                    System.out.println("Вы ввели некорретные данные, попробуйте еще раз.");
                }
            }
            Converter.toJSON(out, new Messege(finalres));
            if(finalres.equals("q")){
                break;
            }
            Messege messege = Converter.toMessege(in);
            if (messege.getMess().equals(">"))
            {
                System.out.println("Число должно быть больше.  Попробуйте еще раз");
            }
            if (messege.getMess().equals("<"))
            {
                System.out.println("Число должно быть меньше.  Попробуйте еще раз");
            }
            if (messege.getMess().equals("="))
            {
                System.out.println("Вы угадали число, поздравляю.");
                break;
            }
            if (messege.getMess().equals("q"))
            {
                System.out.println("Ваш оппонент отключился");
                break;
            }
        }
    }
}
