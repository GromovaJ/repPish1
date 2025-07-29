package HW5;
/*Задача 1. Для введенной с клавиатуры буквы английского алфавита
нужно вывести слева стоящую букву на стандартной клавиатуре. При этом
клавиатура замкнута, т.е. справа от буквы «p» стоит буква «a», а слева от "а"
буква "р", также соседними считаются буквы «l» и буква «z», а буква «m» с
буквой «q».
Входные данные: строка входного потока содержит один символ —
маленькую букву английского алфавита.
Выходные данные: следует вывести букву стоящую слева от заданной
буквы, с учетом замкнутости клавиатуры.*/

import java.util.Scanner;

public class Exc1 {
    public static void main(String[] args) {
        String str = "qwertyuiopasdfghjklzxcvbnm";
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите одну букву английского алфавита (строчную): ");
        String input = scan.nextLine();
        //Если введено несколько букв, то берем тольк первую
        char letter = input.charAt(0);
        System.out.println("Введена буква: "+letter);
        //Найдем индекс первого вхождения введенной буквы в строке (на клавиатуре)
        int ind = str.indexOf(letter);
        if (ind == -1) {
            System.out.println("Ошибка! Введен неверный символ");
            scan.close();
            return;
        }
        //Найдем предыдущий индекс для буквы, стоящей слева на клавиатуре
        int prevInd = (ind - 1 + str.length())%str.length();
        // System.out.println("prevInd=" + prevInd);
        System.out.println("Буква, стоящая слева на клавиатуре: " + str.charAt(prevInd));
        scan.close();
    }
}

