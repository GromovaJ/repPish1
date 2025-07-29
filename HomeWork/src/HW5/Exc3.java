package HW5;
/*Задача 3*. Задана строка, состоящая из букв английского алфавита,
разделенных одним пробелом. Необходимо каждую последовательность
символов упорядочить по возрастанию и вывести слова в нижнем регистре.
Входные данные: в единственной строке последовательность символов
представляющее два слова.
Выходные данные: упорядоченные по возрастанию буквы в нижнем регистре*/

import java.util.Arrays;
import java.util.Scanner;

class Exc3 {
    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите последовательность символов,представляющую два слова разделенных пробелом:");
        String str = scan.nextLine();
        //Приводим введенную строку к нижнему регистру
        String lowerStr = str.toLowerCase();
        //Введенную строку разбиваем на массив слов
        String[] words = lowerStr.split(" ");
        //Проверка правильности ввода строки
        if (words.length !=2){
            System.out.println("Ошибка входных данных! Введено должно быть два слова");
            scan.close();
            return;
        }
        System.out.println("Введенная строка в нижнем регистре: " + lowerStr);
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            System.out.println("Исходное слово: " + word);
            //Каждое слово преобразуем  в массив символов
            char[] letters = word.toCharArray();
            //Сортируем символы каждого слова по возрастанию
            Arrays.sort(letters);
            //Собираем отсортированные символы обратно в строку
            String sortWord = new String(letters);
            System.out.println("Отсортированное слово: " + sortWord);
            result.append(sortWord).append(" ");
        }
        String resultStr = result.toString().trim();
        System.out.println("Результирующая строка: " + resultStr);
        scan.close();
    }
}

