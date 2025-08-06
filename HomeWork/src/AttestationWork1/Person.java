package AttestationWork1;

import java.util.ArrayList;

class Person {
    //Приватные поля для хранения имени покупателя, суммы денег, списка продуктов покупателя
    private String name;
    private double money;
    private ArrayList<Product> products;

    //Конструктор класса Person с параметрами name и money
    public Person(String name, double money) {
        setName(name); //Установка имени с помощью сеттера (включает валидацию)
        setMoney(money); //Установка суммы денег с помощью сеттера (включает валидацию)
        this.products = new ArrayList<>(); //Инициализация списка продуктов пустым ArrayList
    }
    //Геттер для получения имени покупателя
    public String getName() {
        return name;
    }
    //Сеттер для установки имени покупателя с валидацией
    public void setName(String name) {
        //Проверка, что имя не null и не пустая строка (после trim())
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Имя покупателя не может пустой строкой");
        }
        //Проверка минимальной длины имени (3 символа)
        if (name.length() < 3){
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
        this.name = name; //Установка значения поля, если валидация пройдена
    }
    //Геттер для получения суммы денег покупателя
    public double getMoney() {
        return money;
    }
    //Сеттер для установки суммы денег с валидацией
    public void setMoney(double money) {
        //Проверка, что сумма денег не отрицательная
        if (money < 0){
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.money = money; //Установка значения поля, если валидация пройдена
    }
    //Геттер для получения списка продуктов покупателя
    public ArrayList<Product> getProducts() {
        return products;
    }

    //Метод проверки возможности покупки продукта (достаточно ли у покупателя денег)
    public boolean canBuy(Product product) {
        //Возвращает true, если у покупателя достаточно денег для покупки
        return money >= product.getPrice();
    }

    //Метод совершения покупки продукта
    public void buyProduct(Product product) {
        //Уменьшение суммы денег на цену продукта
        money-=product.getPrice();
        //Добавим продукт в список покупок
        products.add(product);
    }

    //Переопределение метода toString() для строкового представления объекта,
    //чтобы выводилась понятная информация о его полях, а не технический хэш-код
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", money=" + money + '\'' +
                ", products=" + products +
                '}';
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

