package HW4;

import java.util.Random;

public class TV {
    // Поля
    private String brand;
    private Integer diagonal;
    private Integer frequency;
    private boolean isOn;

    // Конструктор
    public TV(String brand, Integer diagonal, Integer refresh, boolean isOn) {
        this.brand = brand;
        this.diagonal = diagonal;
        this.frequency = refresh;
        this.isOn = isOn;
    }

    // Геттеры и сеттеры (свойства)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(Integer diagonal) {
        this.diagonal = diagonal;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    // Методы управления телевизором
    public void turnOn() {
        isOn = true;
        System.out.println("Телевизор " + brand + " включен.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println("Телевизор " + brand + " выключен.");
    }

    // Метод генерации телевизора случайным выбором
    public static TV randomTV() {
        Random rand = new Random();
        String [] brands = {"Sony", "Haier", "Samsung"};
        String randomBrand = brands[rand.nextInt(brands.length)];
        Integer[] diagonals = {55, 65, 75};
        Integer randomDiagonal = diagonals[rand.nextInt(diagonals.length)];
        Integer[] frequencies = {90, 100, 120};
        Integer randomFrequency = diagonals[rand.nextInt(frequencies.length)];
        Boolean[] isOnOff = {true, false};
        Boolean randomIsOn = isOnOff[rand.nextInt(isOnOff.length)];

        return new TV(randomBrand, randomDiagonal, randomFrequency, randomIsOn);
    }
}
