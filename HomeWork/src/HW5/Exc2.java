package HW5;

/*Задача 2. Задана последовательность, состоящая только из символов ‘>’,
‘<’ и ‘-‘. Требуется найти количество стрел, которые спрятаны в этой
последовательности. Стрелы – это подстроки вида ‘>>-->’ и ‘<--<<’.
Входные данные: в первой строке входного потока записана строка,
состоящая из символов ‘>’, ‘<’ и ‘-‘ (без пробелов). Строка может содержать до
106 символов.
Выходные данные: в единственную строку выходного потока нужно
вывести искомое количество стрелок.*/

import java.util.Scanner;

class Exc2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите строку, состоящую из символов ‘>’, ‘<’ и ‘-‘ (без пробелов):");
        String str = scan.nextLine();
        //Проверки на правильность ввода строки
        if (str.length() > 106) {
            System.out.println("Введено символов в строке: " + str.length());
            System.out.println("Строка может содержать не более 106 символов");
            return;
        }
        if (str.indexOf(" ") != -1) {
            System.out.println("Строка содержит пробел");
            return;
        }
        System.out.println("Введено символов в строке: " + str.length());
        //Ищем кол-во стрел
        String[] arrows = {">>-->", "<--<<"};
        int cntAll = 0;
        for (String arrow : arrows) {
            int cnt = 0;
            System.out.println(arrow);
            int index = -1;
            while ((index = str.indexOf(arrow, index + 1)) != -1) {
                System.out.println("Найдено на позиции: " + index);
                cnt++;
                cntAll++;
            }
            System.out.println("Найдено стрел " + arrow +":  " + cnt);
        }
        System.out.println("Всего стрел:  " + cntAll);
    }
}

