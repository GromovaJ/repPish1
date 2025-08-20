package AttestationWork1;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //Создим списки для хранения продуктов (products) и покупателей (people)
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();

        //Введем покупателей
        System.out.println("Введите покупателей в формате: Имя = Сумма (через точку с запятой):");
        //Считываем введенную строку и удаляем лишние пробелы в начале и конце
        String strInput = scan.nextLine().trim();
        //Разбиваем введенную строку на массив подстрок (покупатели) по разделителю ";"
        String[] inpPeople = strInput.split(";");
        //Цикл для обработки каждого покупателя из массива
        for (String inpPerson : inpPeople) {
            //Разбиваем строку с информцией о покупателе на массив подстрок (части) по разделителю "="
            //и удаляем лишние пробелы по краям
            String[] inpPersonParts = inpPerson.trim().split("=");
            //Проверяем, что строка разделилась ровно на 2 части (имя и сумма)
            if (inpPersonParts.length != 2) {
                //Если формат неверный, выводим сообщение об ошибке
                System.out.println("Неверный формат ввода покупателя. Формат ввода: Имя = Сумма");
                //Переходим к следующей итерации цикла (следующему покупателю)
                continue;
            }
            //Извлекаем имя покупателя (первая часть разделенной строки) и удаляем лишние пробелы
            String name = inpPersonParts[0].trim();
            //Проверка, что имя не пустая строка
            if (name.trim().isEmpty()){
                System.out.println("Имя покупателя не может пустой строкой");
                return;
            }
            //Проверка минимальной длины имени (3 символа)
            if (name.length() < 3){
                System.out.println("Имя не может быть короче 3 символов");
                return;
            }
            //Извлекаем сумму денег (вторая часть разделенной строки), удаляем пробелы и
            //преобразуем в число типа double
            double money = Double.parseDouble(inpPersonParts[1].trim());
            //Проверка, что сумма денег не отрицательная
            if (money < 0){
                System.out.println("Деньги не могут быть отрицательными");
                return;
            }
            //Создаем новый объект Person (покупателя) с полученными данными (имя покупателя, сумма денег)
            Person person = new Person(name, money);
            //Добавляем созданного покупателя в список people
            people.add(person);
        }

        //Введем продукты
        System.out.println("Введите продукты в формате: Название = Стоимость (через точку с запятой):");
        //Считываем введенную строку от пользователя и удаляем лишние пробелы в начале и конце
        strInput = scan.nextLine().trim();
        //Разбиваем введенную строку на массив подстрок (продукты) по разделителю ";"
        String[] inpProducts = strInput.split(";");
        //Цикл для обработки каждого продукта из массива
        for (String inpProduct : inpProducts) {
            //Разбиваем строку с информацией о продукте на массив подстрок (части) по разделителю "="
            //и удаляем лишние пробелы по краям
            String[] inpProductParts = inpProduct.trim().split("=");
            //Проверяем, что строка разделилась ровно на 2 части (название и цена)
            if (inpProductParts.length != 2) {
                //Если формат неверный, выводим сообщение об ошибке
                System.out.println("Неверный формат ввода продукта. Формат ввода: Наименование = Стоимость");
                //Переходим к следующей итерации цикла (следующему продукту)
                continue;
            }
            //Извлекаем название продукта (первая часть разделенной строки) и удаляем лишние пробелы
            String name = inpProductParts[0].trim();
            //Проверка, что название не пустая строка
            if (name.trim().isEmpty()) {
                System.out.println("Название продукта не может быть пустой строкой");
                return;
            }
            //Извлекаем цену продукта (вторая часть разделенной строки), удаляем пробелы
            //и преобразуем в число типа double
            double price = Double.parseDouble(inpProductParts[1].trim());
            //Проверка, что цена не отрицательная
            if (price < 0) {
                System.out.println("Стоимость продукта не может быть отрицательным числом");
                return;
            }
            //Создаем новый объект Product с полученными данными (название, цена)
            Product product = new Product(name, price);
            //Добавляем созданный продукт в список products
            products.add(product);
        }

        //Покупки
        System.out.println("Введите покупки в формате: Имя покупателя - Наименование продукта (для завершения введите END):");
        //Бесконечный цикл для обработки покупок
        while (true) {
            //Считываем введенную строку с покупками и удаляем лишние пробелы
            strInput = scan.nextLine().trim();
            //Проверяем, не ввел ли пользователь команду завершения "END" (без учета регистра)
            if (strInput.equalsIgnoreCase("END")) break;
            //Разбиваем строку с покупками на массив подстрок (части) по разделителю "-"
            String[] purchaseParts = strInput.split("-");
            //Проверяем, что строка разделилась ровно на 2 части (имя покупателя и продукт)
            if (purchaseParts.length != 2) {
                //Если формат неверный, выводим сообщение об ошибке
                System.out.println("Неверный формат ввода покупки. Формат ввода: Имя покупателя - Наименование продукта");
                //Переходим к следующей итерации цикла (следующей покупке)
                continue;
            }
            //Извлекаем имя покупателя (первая часть) и удаляем лишние пробелы
            String purchPersonName = purchaseParts[0].trim();
            //Извлекаем название продукта (вторая часть) и удаляем лишние пробелы
            String purchProductName = purchaseParts[1].trim();

            //Ищем покупателя в списке people
            //Инициализируем переменную для хранения покупателя, сделавшего покупку
            Person person = null;
            //Перебираем всех покупателей в списке people
            for (Person arrPerson : people)
                //Сравниваем имена покупателей из списка с именем покупателя, сделавшего покупку, без учета регистра
                if (arrPerson.getName().equalsIgnoreCase(purchPersonName)) {
                    //Сохраним найденного покупателя в переменную person
                    person = arrPerson;
                }
            //Проверяем, найден ли покупатель в списке people
            if (person == null) {
                System.out.println("Покупатель не найден: " + purchPersonName);
                continue; //Переходим к следующей покупке
            }

            //Ищем продукт в списке products
            //Инициализируем переменную для хранения продуктов покупки
            Product product = null;
            //Перебираем все продукты в списке products
            for (Product arrProduct : products)
                //Сравниваем наименование продукта из списка с наименованием продукта из покупки, без учета регистра
                if (arrProduct.getName().equalsIgnoreCase(purchProductName)) {
                    //Сохраним найденный продукт в переменную product
                    product = arrProduct;
                }
            //Проверяем, найден ли продукт из покупки в списке products
            if (product == null) {
                System.out.println("Продукт не найден: " + purchProductName);
                continue; //Переходим к следующей покупке
            }

            //Проверяем, может ли покупатель купить продукт
            if(person.canBuy(product)){
                //Если да, то совершаем покупку
                person.buyProduct(product);
                //Выводим сообщение об успешной покупке
                System.out.println(person.getName()  + " купил " + product.getName());
            }
            else {
                //Если нет, то выводим сообщение о невозможности покупки
                System.out.println(person.getName() + " не может позволить себе " + product.getName());
            }
        }

        //Вывод итоговой информации о покупках
        System.out.println("Итоговая информация о покупках:");
        //Перебираем всех покупателей из списка people
        for (Person person : people){
            //Выводим имя текущего покупателя и разделитель " - " без перевода строки
            System.out.print(person.getName() + " - ");
            //Проверяем, есть ли у покупателя купленные продукты
            if (person.getProducts().isEmpty()) {
                //Если список покупок пуст, выводим сообщение
                System.out.print("Ничего не куплено");
            }
            else {
                //Инициализируем счетчик продуктов покупателя для правильной расстановки запятых
                int cntPersProduct = 0;
                //Перебираем все продукты, купленные текущим покупателем
                for (Product product : person.getProducts()) {
                    //Для всех продуктов покупателя, кроме первого, выводим запятую перед названием
                    if (cntPersProduct > 0) {
                        System.out.print(", ");
                    }
                    //Выводим название текущего продукта
                    System.out.print(product.getName());
                    //Увеличиваем счетчик продуктов покупателя
                    cntPersProduct++;
                }
            }
            //Переводим строку после вывода информации о каждом покупателе
            System.out.println();
        }
        //Закрываем объект Scanner
        scan.close();
    }
}
