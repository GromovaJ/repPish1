package HW11.repository;

import HW11.model.Car;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsRepositoryImpl implements CarsRepository {
    //Поле класса
    private List<Car> cars;

    //Конструктор класса по умолчанию
    public CarsRepositoryImpl() {
    }
    //Реализуем метод интерфейса - Загрузка автомобилей из файла
    @Override
    public List<Car> readFromFile(String fileName) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            this.cars = lines.map(line -> {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length != 5) {
                        System.err.println("Некорректный формат строки (ожидается 5 полей): " + line);
                        return null;
                    }
                    return new Car(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Long.parseLong(parts[3].trim()),
                            Long.parseLong(parts[4].trim())
                    );
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка преобразования числа в строке: " + line);
                    return null;
                } catch (Exception e) {
                    System.err.println("Неожиданная ошибка при обработке строки: " + line);
                    return null;
                }

            }).filter(Objects::nonNull).collect(Collectors.toList());
            return this.cars;
        }
    }

    //Реализуем метод интерфейса - Нахождение номера автомобилей по цвету или пробегу
    @Override
    public List<String> findNumberByColorOrMileage(String color, long mileage) {
        if (cars == null || cars.isEmpty()) {
            return List.of();
        }
        return cars.stream()
                .filter(car -> color.equals(car.getColor()) || car.getMileage() == mileage)
                .map(Car::getNumber)
                .toList();
    }

    //Реализуем метод интерфейса - Нахождение количества уникальных моделей автомобилей в ценовом диапазоне
    @Override
    public long countUniqueModelsInPriceRange(long minPrice, long maxPrice) {
        if (cars == null || cars.isEmpty()) {
            return 0;
        }
        return cars.stream()
                .filter(car -> car.getPrice() >= minPrice && car.getPrice() <= maxPrice)
                .map(Car::getModel)
                .distinct()
                .count();
    }

    //Реализуем метод интерфейса - Нахождение автомобиля с минимальной стоимостью
    @Override
    public Optional<Car> findCheapestCar() {
        if (cars == null || cars.isEmpty()) {
            return Optional.empty();
        }
        return cars.stream()
                .min((car1, car2) -> Long.compare(car1.getPrice(), car2.getPrice()));
    }

    //Реализуем метод интерфейса - Вычисление средней стоимости указанной модели автомобиля
    @Override
    public double calcAveragePriceByModel(String model) {
        if (cars == null || cars.isEmpty()) {
            return 0;
        }
        return cars.stream()
                .filter(car -> model.equals(car.getModel()))
                .mapToLong(Car::getPrice)
                .average()
                .orElse(0.0);
    }

}
