CREATE TABLE IF NOT EXISTS Category (
    id SERIAL PRIMARY KEY,  -- 카테고리 ID
    category_name VARCHAR(255) NOT NULL  -- 카테고리 이름 (예: 전자기기, 의류, 가구 등)
);