package HW7;

class Product {
    //Приватные поля для хранения названия, цены продукта
    private String name;
    private double price;

    //Конструктор класса Product с параметрами: name - название продукта, price - цена продукта
    public Product(String name, double price){
        //Установка названия продукта
        setName(name);
        //Установка цены
        //this.price = price;
        setPrice(price);
    }
    //Геттер для получения названия продукта
    public String getName() {
        return name;
    }
    //Сеттер для установки названия продукта с валидацией
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Название продукта не может быть короче 3 символов");
        }
        if (name.matches("\\d+$")){
            throw new IllegalArgumentException("Название продукта не может содержать только цифры");
        }
        //Установка значения поля
        this.name = name;
    }

    //Геттер для получения цены продукта
    public double getPrice() {
        return price;
    }
    public double getCurrentPrice() {
        return price;
    }
    //Сеттер для установки цены продукта с валидацией
    public void setPrice(double price) {
        if (price <= 0){
            throw new IllegalArgumentException("Цена продукта должна быть положительной");
        }
        //Установка значения поля
        this.price = price;
    }
    //Переопределение метода toString() для строкового представления объекта,
    //чтобы выводилась понятная информация о его полях, а не технический хэш-код
    @Override
    public String toString() {
        return name + " (Цена: " + price + ")";
    }

    //Переопределение метода equals() для сравнения объектов,
    //чтобы корректно сравнивать объекты между собой по их содержимому (полям), а не по ссылке в памяти
    @Override
    public boolean equals(Object o) {
        //1. Проверка на сравнение объекта с самим собой
        if (this == o) return true;
        //2. Проверка на null и сравнение классов объектов
        if (o == null || getClass() != o.getClass()) return false;
        //3. Приведение типа параметра к Product
        Product product = (Product) o;
        //4. Сравнение цен и названий продуктов
        return Double.compare(product.price, price) == 0 &&
                name.equals(product.name);
    }

    //Переопределение метода hashCode() (обязательно при переопределении equals()),
    //иначе коллекции HashSet/HashMap будут некорректно сравнивать объекты
    @Override
    public int hashCode() {
        //1. Получаем хеш-код названия продукта
        int result = name.hashCode();
        //2. Добавляем хеш-код цены (умноженный на простое число 31)
        result = 31 * result + Double.hashCode(price);
        // 3. Возвращаем итоговый хеш-код
        return result;
    }
}

