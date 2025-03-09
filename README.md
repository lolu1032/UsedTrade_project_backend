# Blog Project Backend

## API 문서
https://documenter.getpostman.com/view/36962815/2sAYdoG7wj

## 기술 스택
- Java
- Spring Boot
- PostgreSQL
- JPA

---

## 프로젝트 구조

```plaintext
├── blog
│   ├── Auth
│   │   ├── controller
│   │   │   ├── HelloController.java
│   │   │   ├── UserController.java
│   │   ├── domain
│   │   │   ├── RefreshToken.java
│   │   │   ├── Users.java
│   │   ├── dto
│   │   │   ├── LoginCommandDtos.java
│   │   ├── exception
│   │   │   ├── LoginErrorCode.java
│   │   │   ├── LoginException.java
│   │   ├── repository
│   │   │   ├── RefreshTokenRepository.java
│   │   │   ├── UserRepository.java
│   │   ├── service
│   │   │   ├── UserService.java
│   │   ├── util
│   │   │   ├── JwtUtil.java
│   ├── common
│   ├── exception
│   ├── ProjectApplication.java
│
├── resources
│   ├── db.migration
│   │   ├── V1_0_0_create_tb_users.sql
│   │   ├── V1_0_1_init_enable_uuid.sql
│   │   ├── V1_0_2_create_tb_refreshToken.sql
|   |   ├── V1_0_3_create_tb_verificationToken.sql
|   |   ├── V1_0_4_create_tb_location.sql
|   |   ├── V1_0_5_create_tb_category.sql
|   |   ├── V1_0_6_create_tb_product.sql
|   |   ├── V1_0_7_create_tb_image.sql
```
## 코드
<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/fc02b7d1-6d7b-496d-b37c-1e529d3e6611" width="48%">
  <img src="https://github.com/user-attachments/assets/ef39aa07-5812-445e-9582-21d6614101bc" width="48%">
</div>

