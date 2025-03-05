CREATE TABLE IF NOT EXISTS Location (
    id SERIAL PRIMARY KEY,   -- 지역 ID
    latitude DECIMAL(9, 6),           -- 위도
    longitude DECIMAL(9, 6),          -- 경도
    region_name VARCHAR(255),         -- 지역명 (예: '서울시 강남구')
    full_address VARCHAR(255)         -- 구체적인 주소
);