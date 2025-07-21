import java.util.Scanner;

public class HW2 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ваше имя: ");
        String name = scanner.nextLine();
        System.out.println("Привет, " + name);
    }
}
