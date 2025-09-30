package HW7_1;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class UniqueElements {
    public static void main (String[] args){
        //Создим новый ArrayList для хранения строк
        ArrayList<String> elements = new ArrayList<>();

        // Добавляем элементы в список (с дубликатами)
        elements.add("Иван");
        elements.add("Петр");
        elements.add("Иван");
        elements.add("Петр");
        elements.add("Иван");
        elements.add("Василий");
        elements.add("Федор");

        // Выводим исходный список с дубликатами
        System.out.println("Исходный   набор элементов: " + elements);

        // Вызываем метод для получения уникальных элементов
        Set<String> unique = getUniqueElements(elements);

        // Выводим результат - набор уникальных элементов
        System.out.println("Уникальный набор элементов: " + unique);
    }

    // Generic-метод для получения уникальных элементов из любого типа данных
    public static<T> Set<T> getUniqueElements (ArrayList<T> arList) {
        // Создаем LinkedHashSet из переданного ArrayList
        // LinkedHashSet автоматически удаляет дубликаты и сохраняет порядок элементов
        return new LinkedHashSet<>(arList);
    }
}

