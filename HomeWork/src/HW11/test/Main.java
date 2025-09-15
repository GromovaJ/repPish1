package HW11.test;

import HW11.repository.CarsRepository;
import HW11.repository.CarsRepositoryImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        //Вывод результатов в файл
        String outputFileName = "HomeWork/src/HW11/data/output_results.txt";
        try(PrintWriter writer = new PrintWriter(outputFileName)) {
            //Создаем репозиторий
            CarsRepository repository = new CarsRepositoryImpl();

            //Чтение данных из исходного файла
            String fileName = "HomeWork/src/HW11/data/cars.txt";
            var cars = repository.readFromFile(fileName);

            //Вывод результатов в файл
            writer.println("Всего автомобилей (" + cars.size() + " шт.):");
            cars.forEach(writer::println);
            writer.println();

            //Вывод результатов на экран
            System.out.println("Всего автомобилей (" + cars.size() + " шт.):");
            cars.forEach(System.out::println);
            System.out.println();


            // Тестовые данные
            String colorToFind = "Black";
            long mileageToFind = 0L;
            long minPrice = 700_000L;
            long maxPrice = 800_000L;
            String modelToFind = "Toyota";
            String otherModelToFind = "Volvo";

            // 1) Номера всех автомобилей, имеющих заданный цвет или пробег
            var filteredNumbers = repository.findNumberByColorOrMileage(colorToFind, mileageToFind);

            //Вывод результатов в файл
            writer.println("1. Номера автомобилей с цветом '" + colorToFind + "' или пробегом " + mileageToFind + ":");
            if (filteredNumbers.isEmpty()){
                writer.println("Автомобили не найдены");
            } else {
                filteredNumbers.forEach(writer::println);
            }
            writer.println();

            //Вывод результатов на экран
            System.out.println("1. Номера автомобилей с цветом '" + colorToFind + "' или пробегом " + mileageToFind + ":");
            if (filteredNumbers.isEmpty()){
                System.out.println("Автомобили не найдены");
            } else {
                filteredNumbers.forEach(System.out::println);
            }
            System.out.println();

            // 2) Количество уникальных моделей в ценовом диапазоне
            long uniqueModels = repository.countUniqueModelsInPriceRange(minPrice, maxPrice);

            //Вывод результатов в файл
            writer.print("2. Количество уникальных моделей в ценовом диапазоне " + minPrice + " - " + maxPrice + ": ");
            writer.println( uniqueModels + " шт");
            writer.println();

            //Вывод результатов на экран
            System.out.print("2. Количество уникальных моделей в ценовом диапазоне " + minPrice + " - " + maxPrice + ": ");
            System.out.println( uniqueModels + " шт");
            System.out.println();

            // 3) Цвет автомобиля с минимальной стоимостью
            var cheapestCar  = repository.findCheapestCar();

            //Вывод результатов в файл
            writer.print("3. Цвет автомобиля с минимальной стоимостью: ");
            if (cheapestCar.isPresent()) {
                writer.println(cheapestCar.get().getColor());
                writer.println("   " + cheapestCar.get());
            } else {
                writer.println("Автомобили не найдены");
            }
            writer.println();

            //Вывод результатов на экран
            System.out.print("3. Цвет автомобиля с минимальной стоимостью: ");
            if (cheapestCar.isPresent()) {
                System.out.println(cheapestCar.get().getColor());
                System.out.println("   " + cheapestCar.get());
            } else {
                System.out.println("Автомобили не найдены");
            }
            System.out.println();

            // 4) Средняя стоимость искомой модели
            double averagePrice = repository.calcAveragePriceByModel(modelToFind);

//Вывод результатов в файл
            writer.print("4. Средняя стоимость модели '" + modelToFind + "': ");
            writer.println(String.format("%.2f", averagePrice));

            //Вывод результатов на экран
            System.out.print("4. Средняя стоимость модели '" + modelToFind + "': ");
            System.out.printf("%.2f%n", averagePrice);

            // Средняя стоимость другой искомой модели
            double otherAveragePrice = repository.calcAveragePriceByModel(otherModelToFind);

            //Вывод результатов в файл
            writer.print("   Средняя стоимость модели '" + otherModelToFind + "': ");
            writer.println(String.format("%.2f", otherAveragePrice));

            //Вывод результатов на экран
            System.out.print("   Средняя стоимость модели '" + otherModelToFind + "': ");
            System.out.printf("%.2f%n", otherAveragePrice);
            System.out.println();

            System.out.println("Результаты успешно записаны в файл: " + Paths.get(outputFileName).toAbsolutePath());
        } catch(IOException e){
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
            e.printStackTrace();
        } catch(Exception e){
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
