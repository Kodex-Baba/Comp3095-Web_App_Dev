CREATE TABLE t_orders (
    id BIGSERIAL NOT NULL,
    order_number VARCHAR(225) DEFAULT NULL,
    sku_code VARCHAR(225),
    price DECIMAL(19, 2),
    quantity INT,
    PRIMARY KEY (id)
);