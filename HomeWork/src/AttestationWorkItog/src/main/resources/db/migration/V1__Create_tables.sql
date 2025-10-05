-- Создание таблиц для системы учёта заказов

-- Таблица статусов заказов (справочник)
CREATE TABLE IF NOT EXISTS order_status (
    id SERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE order_status IS 'Справочник статусов заказов';
COMMENT ON COLUMN order_status.id IS 'Уникальный идентификатор статуса';
COMMENT ON COLUMN order_status.status_name IS 'Наименование статуса заказа';

-- Таблица продуктов
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    quantity INTEGER NOT NULL CHECK (quantity >= 0),
    category VARCHAR(100) NOT NULL
);

COMMENT ON TABLE products IS 'Таблица товаров/продуктов';
COMMENT ON COLUMN products.id IS 'Уникальный идентификатор продукта';
COMMENT ON COLUMN products.description IS 'Описание продукта';
COMMENT ON COLUMN products.price IS 'Стоимость продукта (должна быть >= 0)';
COMMENT ON COLUMN products.quantity IS 'Количество на складе (должно быть >= 0)';
COMMENT ON COLUMN products.category IS 'Категория продукта';

-- Таблица клиентов
CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

COMMENT ON TABLE customers IS 'Таблица клиентов';
COMMENT ON COLUMN customers.id IS 'Уникальный идентификатор клиента';
COMMENT ON COLUMN customers.first_name IS 'Имя клиента';
COMMENT ON COLUMN customers.last_name IS 'Фамилия клиента';
COMMENT ON COLUMN customers.phone IS 'Телефон клиента';
COMMENT ON COLUMN customers.email IS 'Email клиента (уникальный)';

-- Таблица заказов
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id),
    customer_id INTEGER NOT NULL REFERENCES customers(id),
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    status_id INTEGER NOT NULL REFERENCES order_status(id)
);

COMMENT ON TABLE orders IS 'Таблица заказов';
COMMENT ON COLUMN orders.id IS 'Уникальный идентификатор заказа';
COMMENT ON COLUMN orders.product_id IS 'Внешний ключ к продукту';
COMMENT ON COLUMN orders.customer_id IS 'Внешний ключ к клиенту';
COMMENT ON COLUMN orders.order_date IS 'Дата оформления заказа';
COMMENT ON COLUMN orders.quantity IS 'Количество товара в заказе (должно быть > 0)';
COMMENT ON COLUMN orders.status_id IS 'Внешний ключ к статусу заказа';

-- Создание индексов для улучшения производительности

-- Индексы для внешних ключей
CREATE INDEX IF NOT EXISTS idx_orders_product_id ON orders(product_id);
CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders(customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status_id ON orders(status_id);

-- Индекс по дате заказа для быстрого поиска по датам
CREATE INDEX IF NOT EXISTS idx_orders_date ON orders(order_date);

-- Индекс по email клиента для быстрого поиска
CREATE INDEX IF NOT EXISTS idx_customers_email ON customers(email);

-- Индекс по категории продукта для фильтрации
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);