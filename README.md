# Blog Project Backend

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
```
## 코드
<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/fc02b7d1-6d7b-496d-b37c-1e529d3e6611" width="48%">
  <img src="https://github.com/user-attachments/assets/ef39aa07-5812-445e-9582-21d6614101bc" width="48%">
</div>

