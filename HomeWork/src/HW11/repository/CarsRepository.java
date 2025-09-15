package HW11.repository;

import HW11.model.Car;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CarsRepository {
    /**
     * Загрузка автомобилей из файла
     * @param fileName имя файла
     * @return список автомобилей
     */
    List<Car> readFromFile(String fileName) throws IOException;

    /**
     * Нахождение номера автомобилей по цвету или пробегу
     * @param color цвет для поиска
     * @param mileage пробег для поиска
     * @return список номеров автомобилей
     */
    List<String>findNumberByColorOrMileage(String color, long mileage);

    /**
     * Нахождение количества уникальных моделей автомобилей в ценовом диапазоне
     * @return Optional с цветом автомобиля минимальной стоимости
     */
    long countUniqueModelsInPriceRange(long minPrice, long maxPrice);

    /**
     * Нахождение автомобиля с минимальной стоимостью
     * @return Optional автомобиля минимальной стоимости
     */
    Optional<Car> findCheapestCar();

    /**
     * Вычисление средней стоимости указанной модели автомобиля
     * @param model модель для поиска
     * @return средняя стоимость
     */
    double calcAveragePriceByModel(String model);

}
