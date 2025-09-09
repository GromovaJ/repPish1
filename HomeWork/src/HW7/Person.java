package HW7;

import java.util.ArrayList;
import java.util.List;

class Person {
    //Приватные поля для хранения имени покупателя, суммы денег, списка продуктов покупателя
    private String name;
    private double money;
    private List<Product> products;

    //Конструктор класса Person с параметрами name и money
    public Person(String name, double money) {
        setName(name); //Установка имени
        setMoney(money); //Установка суммы денег
        this.products = new ArrayList<>(); //Инициализация списка продуктов пустым ArrayList
    }
    //Геттер для получения имени покупателя
    public String getName() {
        return name;
    }
    //Сеттер для установки имени покупателя с валидацией
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3){
            throw new IllegalArgumentException("Имя не может быть менее 3 символов");
        }
        this.name = name; //Установка значения поля
    }
    //Сеттер для установки суммы денег с валидацией
    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.money = money; //Установка значения поля
    }
    //Геттер для получения списка продуктов покупателя
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    //Метод проверки возможности покупки продукта (достаточно ли у покупателя денег)
    public boolean canBuy(Product product) {
        //Возвращает true, если у покупателя достаточно денег для покупки
        //return money >= product.getPrice();
        return money >= product.getCurrentPrice();
    }

    //Метод совершения покупки продукта
    public void buyProduct(Product product) {
        if (canBuy(product)) {
            //Уменьшение суммы денег на цену продукта
            //this.money -= product.getPrice();
            money -= product.getCurrentPrice();
            //Добавим продукт в список покупок
            products.add(product);
        }
        else {
            throw new IllegalStateException(String.format("%s не может позволить себе %s", this.name, product.getName()));
        }
    }

    //Переопределение метода toString() для строкового представления объекта,
    //чтобы выводилась понятная информация о его полях, а не технический хэш-код
    @Override
    public String toString() {
        if (products.isEmpty()){
            return name + " - Ничего не куплено";
        }

        StringBuilder sb = new StringBuilder(name).append(" - ");
        for (Product product: products){
            //sb.append(product.getName()).append(", ");
            sb.append(product).append(", ");
        }
        return sb.substring(0, sb.length()-2);
    }

    //Переопределение метода equals() для сравнения объектов,
    //чтобы корректно сравнивать объекты между собой по их содержимому (полям), а не по ссылке в памяти
    @Override
    public boolean equals(Object o) {
        // 1. Проверка на сравнение объекта с самим собой
        if (this == o) return true;
        // 2. Проверка на null и сравнение классов объектов
        if (o == null || getClass() != o.getClass()) return false;
        // 3. Приведение типа параметра к Person
        Person person = (Person) o;
        // 4. Сравнение всех значимых полей
        return Double.compare(person.money, money) == 0 &&
                name.equals(person.name) &&
                products.equals(person.products);
    }

    //Переопределение метода hashCode() (обязательно при переопределении equals()),
    //иначе коллекции HashSet/HashMap будут некорректно сравнивать объекты
    @Override
    public int hashCode() {
        // 1. Получаем хеш-код имени
        int result = name.hashCode();
        // 2. Добавляем хеш-код суммы денег (умноженный на простое число 31)
        result = 31 * result + Double.hashCode(money);
        // 3. Добавляем хеш-код списка продуктов (умноженный на простое число 31)
        result = 31 * result + products.hashCode();
        // 4. Возвращаем итоговый хеш-код
        return result;
    }
}

