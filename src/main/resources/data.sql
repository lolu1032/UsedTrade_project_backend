-- Location 테이블 생성
CREATE TABLE Location (
    id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    region_name VARCHAR(100),
    full_address TEXT
);

-- Category 테이블 생성
CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(50)
);

-- Product 테이블 생성
CREATE TABLE Product (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    price INT,
    status VARCHAR(20),
    views INT,
    user_id INT,
    location_id INT,
    category_id INT,
    FOREIGN KEY (location_id) REFERENCES Location(id),
    FOREIGN KEY (category_id) REFERENCES Category(id)
);
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE chat_rooms (
    room_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- 예시 데이터 삽입
INSERT INTO users (username, email, password) VALUES
('user1', 'user1@example.com', 'password123'),
('user2', 'user2@example.com', 'password456'),
('user3', 'user3@example.com', 'password789');

-- Location 데이터 삽입
INSERT INTO Location (latitude, longitude, region_name, full_address) VALUES
(37.5665, 126.9780, '서울시 종로구', '서울특별시 종로구 세종대로 1'),
(37.5079, 127.0600, '서울시 강남구', '서울특별시 강남구 테헤란로 425'),
(35.1796, 129.0756, '부산시 해운대구', '부산광역시 해운대구 해운대해변로 1'),
(35.6895, 139.6917, '도쿄', '일본 도쿄도 지요다구'),
(37.7749, -122.4194, '샌프란시스코', '미국 캘리포니아주 샌프란시스코');

-- Category 데이터 삽입
INSERT INTO Category (category_name) VALUES
('전자기기'),
('의류'),
('가구'),
('스포츠'),
('도서'),
('음악'),
('악세서리'),
('주방용품'),
('화장품'),
('애완동물');

-- Product 데이터 삽입
INSERT INTO Product (title, description, price, status, views, user_id, location_id, category_id) VALUES
('아이폰 13', '새 제품, 미개봉', 1000000, 'NEW', 0, 1, 1, 1),
('삼성 TV', '사용감 있는 TV, 정상 작동', 500000, 'USED', 0, 2, 1, 3),
('운동화', '새 상품, 사이즈 270', 80000, 'NEW', 0, 1, 2, 2),
('소파', '3인용 소파, 약간의 생활 스크래치 있음', 150000, 'USED', 0, 1, 3, 3),
('노트북', '맥북 에어 2020, 상태 매우 좋음', 1200000, 'NEW', 0, 1, 1, 1),
('자전거', '새 자전거, 26인치', 300000, 'NEW', 0, 1, 2, 4),
('책상', '원목 책상, 약간의 사용 흔적 있음', 120000, 'USED', 0, 1, 3, 3),
('디지털 카메라', '소니 카메라, 새 제품', 500000, 'NEW', 0, 1, 2, 1),
('화장품 세트', '다양한 화장품 포함 세트', 70000, 'NEW', 0, 1, 1, 9),
('강아지 사료', '건강한 강아지 사료 1kg', 15000, 'NEW', 0, 1, 4, 10);
