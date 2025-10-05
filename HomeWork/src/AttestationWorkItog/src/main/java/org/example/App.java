package org.example;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class App {
    private static Properties properties;

    public static void main(String[] args) {
        Connection connection = null;

        try {
            properties = loadProperties();
            runFlywayMigrations();
            connection = getDatabaseConnection();
            demonstrateCRUDOperations(connection);

        } catch (Exception e) {
            System.err.println("Ошибка в приложении: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Соединение с базой данных закрыто");
                } catch (SQLException e) {
                    System.err.println("Ошибка при закрытии соединения");
                }
            }
        }
    }

    /**
     * Демонстрация CRUD-операций
     */
    private static void demonstrateCRUDOperations(Connection connection) {
        System.out.println("\n--- Демонстрация CRUD операций ---");

        try {
            connection.setAutoCommit(false);

            displayAllData(connection);
            Long productId = insertNewProduct(connection);
            Long customerId = insertNewCustomer(connection);
            Long orderId = createOrder(connection, customerId, productId);
            updateProductQuantity(connection, productId);
            deleteTestRecords(connection, orderId, customerId, productId);

            connection.commit();
            System.out.println("Все операции успешно выполнены");

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении операций: " + e.getMessage());
            try {
                connection.rollback();
                System.out.println("Транзакция откатана");
            } catch (SQLException rollbackEx) {
                System.err.println("Ошибка при откате транзакции: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Ошибка при восстановлении автокоммита: " + e.getMessage());
            }
        }
    }

    /**
     * 1. Чтение и вывод всех данных
     */
    private static void displayAllData(Connection connection) throws SQLException {
        System.out.println("\n1. Текущие данные в базе:");

        System.out.println("\n   Статусы заказов:");
        String statusSql = "SELECT * FROM order_status";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(statusSql)) {
            while (rs.next()) {
                System.out.println("   - " + rs.getInt("id") + ". " + rs.getString("status_name"));
            }
        }

        System.out.println("\n   Продукты (первые 5 записей):");
        String productSql = "SELECT * FROM products LIMIT 5";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(productSql)) {
            while (rs.next()) {
                System.out.printf("   - %d. %s - %.2f руб. (%d шт.)%n",
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"));
            }
        }

        System.out.println("\n   Клиенты (первые 5 записей):");
        String customerSql = "SELECT * FROM customers LIMIT 5";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(customerSql)) {
            while (rs.next()) {
                System.out.printf("   - %d. %s %s - %s%n",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"));
            }
        }

        System.out.println("\n   Заказы (первые 5 записей):");
        String orderSql = "SELECT o.id, c.first_name, c.last_name, p.description, " +
                "o.quantity, s.status_name, o.order_date " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.id " +
                "JOIN products p ON o.product_id = p.id " +
                "JOIN order_status s ON o.status_id = s.id " +
                "LIMIT 5";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(orderSql)) {
            while (rs.next()) {
                System.out.printf("   - Заказ #%d: %s %s - %s (%d шт.) - %s%n",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("status_name"));
            }
        }
    }

    /**
     * 2. Вставка нового товара
     */
    private static Long insertNewProduct(Connection connection) throws SQLException {
        System.out.println("\n2. Добавление нового товара");

        String sql = "INSERT INTO products (description, price, quantity, category) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "Новый смартфон Xiaomi 14");
            pstmt.setBigDecimal(2, new java.math.BigDecimal("59999.99"));
            pstmt.setInt(3, 25);
            pstmt.setString(4, "Электроника");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Long productId = rs.getLong("id");
                System.out.println("   Товар добавлен с ID: " + productId);
                return productId;
            }
        }
        throw new SQLException("Не удалось добавить товар");
    }

    /**
     * 3. Вставка нового клиента
     */
    private static Long insertNewCustomer(Connection connection) throws SQLException {
        System.out.println("\n3. Добавление нового клиента");

        String sql = "INSERT INTO customers (first_name, last_name, phone, email) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "Александр");
            pstmt.setString(2, "Новиков");
            pstmt.setString(3, "+79169998877");
            pstmt.setString(4, "alex.novikov@mail.com");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Long customerId = rs.getLong("id");
                System.out.println("   Клиент добавлен с ID: " + customerId);
                return customerId;
            }
        }
        throw new SQLException("Не удалось добавить клиента");
    }

    /**
     * 4. Создание заказа
     */
    private static Long createOrder(Connection connection, Long customerId, Long productId) throws SQLException {
        System.out.println("\n4. Создание заказа");

        String sql = "INSERT INTO orders (product_id, customer_id, order_date, quantity, status_id) " +
                "VALUES (?, ?, CURRENT_DATE, ?, ?) RETURNING id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, productId);
            pstmt.setLong(2, customerId);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, 1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Long orderId = rs.getLong("id");
                System.out.println("   Заказ создан с ID: " + orderId);
                return orderId;
            }
        }
        throw new SQLException("Не удалось создать заказ");
    }

    /**
     * 5. Обновление количества товара
     */
    private static void updateProductQuantity(Connection connection, Long productId) throws SQLException {
        System.out.println("\n5. Обновление количества товара");

        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 20);
            pstmt.setLong(2, productId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("   Количество товара обновлено: 20 шт.");
            } else {
                System.out.println("   Товар не найден");
            }
        }
    }

    /**
     * 6. Удаление тестовых записей
     */
    private static void deleteTestRecords(Connection connection, Long orderId, Long customerId, Long productId) throws SQLException {
        System.out.println("\n6. Удаление тестовых данных");

        try {
            String deleteOrderSql = "DELETE FROM orders WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteOrderSql)) {
                pstmt.setLong(1, orderId);
                int ordersDeleted = pstmt.executeUpdate();
                System.out.println("   Удалено заказов: " + ordersDeleted);
            }

            String deleteCustomerSql = "DELETE FROM customers WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteCustomerSql)) {
                pstmt.setLong(1, customerId);
                int customersDeleted = pstmt.executeUpdate();
                System.out.println("   Удалено клиентов: " + customersDeleted);
            }

            String deleteProductSql = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteProductSql)) {
                pstmt.setLong(1, productId);
                int productsDeleted = pstmt.executeUpdate();
                System.out.println("   Удалено товаров: " + productsDeleted);
            }

        } catch (SQLException e) {
            System.err.println("   Ошибка при удалении тестовых данных: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Получение подключения к базе данных
     */
    private static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String driver = properties.getProperty("db.driver");

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        if (connection != null && !connection.isClosed()) {
            System.out.println("Успешное подключение к базе данных PostgreSQL");
            System.out.println("URL: " + url);
            System.out.println("Пользователь: " + username);

            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT version();");
            if (resultSet.next()) {
                System.out.println("Версия PostgreSQL: " + resultSet.getString(1));
            }
            statement.close();
        }

        return connection;
    }

    /**
     * Запуск миграций базы данных с помощью Flyway
     */
    private static void runFlywayMigrations() {
        System.out.println("Запуск миграций Flyway...");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(
                            properties.getProperty("db.url"),
                            properties.getProperty("db.username"),
                            properties.getProperty("db.password")
                    )
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();

            MigrateResult result = flyway.migrate();
            System.out.println("Миграции успешно выполнены");
            System.out.println("Количество примененных миграций: " + result.migrationsExecuted);

        } catch (Exception e) {
            System.err.println("Ошибка при выполнении миграций: " + e.getMessage());
            throw new RuntimeException("Миграции не выполнены", e);
        }
    }


    /**
     * Метод для загрузки настроек из application.properties
     */
    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        // Пытаемся загрузить из classpath
        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                System.out.println("Файл конфигурации найден в classpath");
                properties.load(inputStream);
                return properties;
            }
        }

        // Если не нашли в classpath, пытаемся загрузить из корня проекта
        System.out.println("Файл не найден в classpath, поиск в корне проекта...");
        try (InputStream inputStream = new FileInputStream("application.properties")) {
            System.out.println("Файл конфигурации найден в корне проекта");
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException e) {
            throw new IOException("Файл application.properties не найден ни в classpath, ни в корне проекта!", e);
        }
    }
}