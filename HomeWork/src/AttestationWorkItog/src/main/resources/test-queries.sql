-- SQL-запросы для тестирования базы данных

-- === ЗАПРОСЫ НА ЧТЕНИЕ (5 запросов) ===

-- 1. Список всех заказов за последние 7 дней с именем покупателя и описанием товара
SELECT
    o.id as order_id,
    o.order_date,
    c.first_name || ' ' || c.last_name as customer_name,
    p.description as product_name,
    o.quantity,
    s.status_name
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN products p ON o.product_id = p.id
JOIN order_status s ON o.status_id = s.id
WHERE o.order_date >= CURRENT_DATE - INTERVAL '7 days'
--CURRENT_DATE - 7
ORDER BY o.order_date DESC;

-- 2. Топ-3 самых популярных товара (по количеству заказов)
SELECT
    p.description as product_name,
    p.category,
    COUNT(o.id) as order_count,
    SUM(o.quantity) as total_quantity_ordered
FROM products p
JOIN orders o ON p.id = o.product_id
GROUP BY p.id, p.description, p.category
ORDER BY order_count DESC, total_quantity_ordered DESC
LIMIT 3;

-- 3. Клиенты с наибольшим количеством заказов
SELECT
    c.first_name || ' ' || c.last_name as customer_name,
    c.email,
    COUNT(o.id) as total_orders,
    SUM(o.quantity) as total_items_ordered
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, customer_name, c.email
ORDER BY total_orders DESC, total_items_ordered DESC;

-- 4. Заказы по статусам с общей суммой
SELECT
    s.status_name,
    COUNT(o.id) as order_count,
    SUM(p.price * o.quantity) as total_revenue
FROM order_status s
LEFT JOIN orders o ON s.id = o.status_id
LEFT JOIN products p ON o.product_id = p.id
GROUP BY s.id, s.status_name
ORDER BY total_revenue DESC NULLS LAST;

-- 5. Товары с низким запасом на складе (меньше 20)
SELECT
    description as product_name,
    category,
    price,
    quantity as stock_quantity
FROM products
ORDER BY quantity ASC
LIMIT 5;

-- === ЗАПРОСЫ НА ИЗМЕНЕНИЕ (3 запроса) ===

-- 6. Обновление количества на складе при покупке (уменьшение на 1 для товара с ID=1)
UPDATE products
SET quantity = quantity - 1
WHERE id = 1 AND quantity > 0;

-- 7. Увеличение цены на 10% для товаров категории "Электроника"
UPDATE products
SET price = price * 1.10
WHERE category = 'Электроника';

-- 8. Обновление статуса заказа на "Доставлен" для заказов старше 3 дней
UPDATE orders
SET status_id = (SELECT id FROM order_status WHERE status_name = 'Доставлен')
WHERE order_date <= CURRENT_DATE - INTERVAL '3 days'
AND status_id != (SELECT id FROM order_status WHERE status_name = 'Доставлен');

-- === ЗАПРОСЫ НА УДАЛЕНИЕ (2 запроса) ===

-- 9. Удаление клиентов без заказов
DELETE FROM customers
WHERE id NOT IN (SELECT customer_id FROM orders);

-- 10. Удаление отмененных заказов старше 30 дней
DELETE FROM orders
WHERE status_id = (SELECT id FROM order_status WHERE status_name = 'Отменен')
AND order_date <= CURRENT_DATE - 30;


