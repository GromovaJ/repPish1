package HW7;

import java.time.LocalDate;
import java.util.Objects;

class DiscountProduct extends Product {
    private double discount; // Поле для хранения размера скидки
    private LocalDate discountEndDate; // Поле для хранения даты окончания действия скидки

    // Конструктор класса DiscountProduct
    public DiscountProduct(String name, double price, double discount, LocalDate discountEndDate) {
        super(name, price); // Вызов конструктора родительского класса Product с именем и ценой
        setDiscount(discount); // Установка размера скидки с помощью сеттера (для валидации)
        this.discountEndDate = discountEndDate; // Прямое присвоение даты окончания скидки (без валидации)
    }
    // Метод для установки размера скидки с валидацией
    public void setDiscount(double discount) {
        if (discount <= 0 || discount >= getPrice()) {
            // Если условие не выполняется, выбрасываем исключение с сообщением об ошибке
            throw new IllegalArgumentException("Скидка должна быть положительной и меньше цены продукта");
        }
        // Если проверка пройдена, присваиваем значение полю discount
        this.discount = discount;
    }
    // Переопределенный метод для получения текущей цены с учетом скидки
    @Override
    public double getCurrentPrice() {
        // Проверяем, не истекла ли скидка (текущая дата после даты окончания)
        if (LocalDate.now().isAfter(discountEndDate)) {
            // Если скидка истекла, возвращаем базовую цену из родительского класса
            return super.getPrice(); // скидка истекла
        }
        // Если скидка активна, возвращаем цену со скидкой
        return super.getPrice() - discount;
    }
    // Переопределенный метод для строкового представления объекта
    @Override
    public String toString() {
        // Комбинируем строковое представление из родительского класса с информацией о скидке
        return super.toString() + " (Скидка: " + discount + " до " + discountEndDate + ")";
    }
    // Переопределенный метод для сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiscountProduct that = (DiscountProduct) o;
        return Double.compare(that.discount, discount) == 0 &&
                Objects.equals(discountEndDate, that.discountEndDate);
    }
    // Переопределенный метод для вычисления хэш-кода объекта
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discount, discountEndDate);
    }
}
