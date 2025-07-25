package HW4;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //Создадим экземпляр класса Телевизор (tv1)
        TV tv1 = new TV("Philips", 50, 60, false);
        System.out.println("Телевизор1: " + tv1.getBrand() + ", диагональ: " + tv1.getDiagonal() + ", частота: " + tv1.getFrequency()+ ", включен: " + tv1.isOn());

        //Зададим новые значения полей объекта tv1 с клавиатуры
        Scanner scan = new Scanner(System.in);
        System.out.println("Для изменения состояния объекта Телевизор1 введите новые параметры:");
        System.out.print("Наименование бренда:");
        String brand = scan.nextLine();
        tv1.setBrand(brand);
        System.out.print("Диагональ:");
        Integer diagonal = scan.nextInt();
        tv1.setDiagonal(diagonal);
        System.out.print("Частота обновления экрана:");
        Integer frequency = scan.nextInt();
        tv1.setFrequency(frequency);
        //Вызовем метод для включения телевизора
        tv1.turnOn();
        System.out.println("Телевизор1: " + tv1.getBrand() + ", диагональ: " + tv1.getDiagonal() + ", частота: " + tv1.getFrequency()+ ", включен: " + tv1.isOn());

        //Создадим еще один экземпляр класса Телевизор  (tv2)
        TV tv2 = new TV("Xiaomi", 43, 60, false);
        System.out.println("Телевизор2: " + tv2.getBrand() + ", диагональ: " + tv2.getDiagonal() + ", частота: " + tv2.getFrequency()+ ", включен: " + tv2.isOn());

        //Создадим случайный экземпляр класса Телевизор  (tv3)
        TV tv3 = TV.randomTV(); // Случайный телевизор
        System.out.println("Телевизор3: " + tv3.getBrand() + ", диагональ: " + tv3.getDiagonal() + ", частота: " + tv3.getFrequency()+ ", включен: " + tv3.isOn());

        scan.close();
    }
}
