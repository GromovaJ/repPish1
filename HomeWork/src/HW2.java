/*Задача 1. Составить программу вывода на экран в одну строку сообщения
        «Привет, имя_пользователя», где «имя_пользователя» - это введёное в консоль
        имя, для выполнения данного задания нужно использовать класс Scanner*/
import java.util.Scanner;

public class HW2 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ваше имя: ");
        String name = scanner.nextLine();
        System.out.println("Привет, " + name);
    }
}
