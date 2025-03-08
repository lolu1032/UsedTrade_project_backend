CREATE TABLE IF NOT EXISTS image (
    id SERIAL PRIMARY KEY,
    image_url VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Product(id)
);