package HW7_1;

import java.util.Arrays;
import java.util.Scanner;


public class Anagram {
    public static void main (String[] args) {
        // Создаем объект Scanner для чтения ввода с клавиатуры
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем первую строку у пользователя
        System.out.println("Введите строку: ");
        // Читаем строку, приводим к нижнему регистру и удаляем все пробелы
        String s = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");
        // Запрашиваем вторую строку у пользователя
        System.out.println("Введите строку: ");
        // Читаем строку, приводим к нижнему регистру и удаляем все пробелы
        String t = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");

        // Вызываем метод isAnagram для проверки являются ли строки анаграммами
        boolean result = isAnagram(s, t);
        System.out.println("Строки являются анаграммами? " + result);
    }

    // Метод для проверки являются ли две строки анаграммами
    public static boolean isAnagram(String s, String t) {
        // Проверяем если любая из строк пустая - выбрасываем исключение
        if (s.isEmpty() || t.isEmpty()){
            throw new IllegalArgumentException("Вводимые строки не могут быть пустыми!");
        }
        //Если длины строк различаются, то они не анаграммы
        if (s.length() != t.length()){
            return false;
        }
        //Преобразуем строки в массивы символов
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        //Отсортируем массивы
        Arrays.sort(sArr);
        Arrays.sort(tArr);

        // Сравниваем отсортированные массивы символов
        return Arrays.equals(sArr, tArr);
    }
}
