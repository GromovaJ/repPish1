package HW7;

import java.time.LocalDate;
import java.util.Objects;

class DiscountProduct extends Product {
    private double discount;
    private LocalDate discountEndDate;


    public DiscountProduct(String name, double price, double discount, LocalDate discountEndDate) {
        super(name, price);
        setDiscount(discount);
        this.discountEndDate = discountEndDate;

    }

    public void setDiscount(double discount) {
        if (discount <= 0 || discount >= getPrice()) {
            throw new IllegalArgumentException("Скидка должна быть положительной и меньше цены продукта");
        }
        this.discount = discount;
    }

    @Override
    public double getCurrentPrice() {
        if (LocalDate.now().isAfter(discountEndDate)) {
            return super.getPrice(); // скидка истекла
        }
        return super.getPrice() - discount;
    }

    @Override
    public String toString() {
        return super.toString() + " (Скидка: " + discount + " до " + discountEndDate + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiscountProduct that = (DiscountProduct) o;
        return Double.compare(that.discount, discount) == 0 &&
                Objects.equals(discountEndDate, that.discountEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discount, discountEndDate);
    }
}
