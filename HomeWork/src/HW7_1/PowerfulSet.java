package HW7_1;

import java.util.HashSet;
import java.util.Set;

public class PowerfulSet {
    public static void main(String[] args) {

        //Исходные данные - Создаем два множества
        Set<Integer> set1 = Set.of(1, 2, 3);
        Set<Integer> set2 = Set.of(0, 1, 2, 4);

        //Вызываем методы
        Set<Integer> intersect = getIntersection(set1, set2);
        Set<Integer> union = getUnion(set1, set2);
        Set<Integer> minus = relativeComplement(set1, set2);

        //Выводим результаты
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        System.out.println("Intersection: " + intersect);
        System.out.println("Union: " + union);
        System.out.println("relativeComplement: " + minus);
    }
    //Метод возвращает пересечение двух наборов (элементы, присутствующие в обоих наборах)
    public static <T> Set<T> getIntersection(Set<T> set1, Set<T> set2) {
        if (set1.isEmpty()|| set2.isEmpty()){
            throw new IllegalArgumentException("Исходные данные не могут быть пустыми!");
        }
        Set<T> result = new HashSet<>(set1); //Создаем копию первого множества
        result.retainAll(set2);              //Добавляем все элементы второго множества (дубли удаляются автоматически)
        return result;                       //Возвращаем результат пересечения
    }
    //Метод возвращает объединения двух наборов (все уникальные элементы из обоих наборов)
    public static <T> Set<T> getUnion(Set<T> set1, Set<T> set2) {
        if (set1.isEmpty()|| set2.isEmpty()){
            throw new IllegalArgumentException("Исходные данные не могут быть пустыми!");
        }
        Set<T> result = new HashSet<>(set1); //Создаем копию первого множества
        result.addAll(set2);                 //Добавляем все элементы второго множества (дубли удаляются автоматически)
        return result;                       //Возвращаем результат объединения
    }
    //Метод возвращает элементы первого набора без тех, которые находятся во втором наборе
    public static <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2) {
        if (set1.isEmpty()|| set2.isEmpty()){
            throw new IllegalArgumentException("Исходные данные не могут быть пустыми!");
        }
        Set<T> result = new HashSet<>(set1); //Создаем копию первого множества
        result.removeAll(set2);              //Удаляем в первом множестве все элементы, которые есть во втором множестве
        return result;                       //Возвращаем результат разности
    }
}

