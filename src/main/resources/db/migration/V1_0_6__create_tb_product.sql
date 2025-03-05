CREATE TABLE IF NOT EXISTS Product (
    id SERIAL PRIMARY KEY,                -- 상품 ID
    title VARCHAR(255) NOT NULL,                   -- 상품 제목
    description TEXT,                              -- 상품 설명
    price DECIMAL(10, 2) NOT NULL,                 -- 판매 가액
    views INT DEFAULT 0,                          -- 조회수
    status VARCHAR(50),                           -- 상품 상태 (ex: NEW, USED)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 등록 시간
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 수정 시간
    user_id BIGINT NOT NULL,                    -- 판매자 ID (user 테이블 참조)
    location_id INT,                              -- 판매 지역 ID (Location 테이블 참조)
    category_id INT,                              -- 카테고리 ID (Category 테이블 참조)
    FOREIGN KEY (user_id) REFERENCES users(id),  -- 판매자 ID
    FOREIGN KEY (location_id) REFERENCES Location(id), -- 위치 정보
    FOREIGN KEY (category_id) REFERENCES Category(id)  -- 카테고리
);