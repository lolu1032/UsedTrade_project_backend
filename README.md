#  중고거래 백엔드

이 프로젝트는 Spring Boot와 PostgreSQL을 활용한 중고거래 프로젝트의 백엔드입니다.

## 목차
- [API 문서](#api-문서)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [코드](#코드)
- [테스트 코드](#테스트코드)
- [설치 방법](#설치-방법)

## API 문서
API 문서 링크는 [여기](https://documenter.getpostman.com/view/36962815/2sAYdoG7wj)에서 확인할 수 있습니다.

## 기술 스택
- Java
- Spring Boot
- PostgreSQL / redis
- JPA
- WebSocket

## 프로젝트 구조

## API 문서
https://documenter.getpostman.com/view/36962815/2sAYdoG7wj

## 기술 스택
- Java
- Spring Boot
- PostgreSQL, redis
- JPA
- WebSocket

---

## 프로젝트 구조

```plaintext
├── App
│   ├── Auth
│   │   ├── controller
│   │   ├── domain
│   │   ├── dto
│   │   ├── exception
│   │   ├── repository
│   │   ├── service
│   │   ├── util
│   ├── Board
│   ├── Chat
│   ├── common
│   ├── exception
│   ├── ProjectApplication.java
│
├── resources
│   ├── db.migration
│   │   ├── V1_0_0__create_tb_users.sql
│   │   ├── V1_0_1__init_enable_uuid.sql
│   │   ├── V1_0_2__create_tb_refreshToken.sql
|   |   ├── V1_0_3__create_tb_verificationToken.sql
|   |   ├── V1_0_4__create_tb_location.sql
|   |   ├── V1_0_5__create_tb_category.sql
|   |   ├── V1_0_6__create_tb_product.sql
|   |   ├── V1_0_7__create_tb_image.sql
|   |   ├── V1_0_8__create_tb_like.sql
|   |   ├── V1_0_9__create_tb_chatRoom.sql
```
## 코드
<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/fc02b7d1-6d7b-496d-b37c-1e529d3e6611" width="48%">
  <img src="https://github.com/user-attachments/assets/ef39aa07-5812-445e-9582-21d6614101bc" width="48%">
</div>

## 테스트코드
<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/5a26aa35-ce77-47d7-90bc-bb725da9fbda" width="48%">
  <img src="https://github.com/user-attachments/assets/4c76de55-5a9a-48d0-86d9-3edfc04efb74" width="48%">
</div>

## 설치 방법
```bash
git clone https://github.com/lolu1032/UsedTrade_project_backend.git

