package HW11.model;

import java.util.Objects;

public class Car {
    //Поля класса
    private final String number;
    private final String model;
    private final String color;
    private final long mileage;
    private final long price;

    //Конструктор класса для инициализации объекта
    public Car(String number, String model, String color, long mileage, long price) {
        this.number = number;
        this.model = model;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
    }
    //Геттеры
    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public long getMileage() {
        return mileage;
    }

    public long getPrice() {
        return price;
    }

    //Переопределенный метод toString(), возвращающий строку представления объекта
    @Override
    public String toString() {
        return "Car{" +
                "number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return mileage == car.mileage && price == car.price && Objects.equals(number, car.number) && Objects.equals(model, car.model) && Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, model, color, mileage, price);
    }
}

