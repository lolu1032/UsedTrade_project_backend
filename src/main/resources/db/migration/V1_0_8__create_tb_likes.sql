CREATE TABLE IF NOT EXISTS likes (
    id SERIAL PRIMARY KEY,
    status BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);