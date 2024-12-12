## 1. 프로젝트 소개

첫번째 프로젝트는 “카페 메뉴 관리 서비스 제작” 팀 프로젝트입니다. 백둥이 여러분은 주어진 요구 사항에 맞추어 프로젝트를 수행하게 됩니다. 

이번 프로젝트를 통해 백엔드 개발을 위한 환경설정을 진행하고 Spring을 이용해서 커피 메뉴 데이터를 관리하는 4가지 로직 CRUD(Create, Read, Update, Delete)를 구현하는 프로젝트를 진행해봅시다.

## 2. 일정 및 팀 구성 안내

📅 **전체 일정**

- 팀 구성 안내: 1~11팀 / 5인 1팀
- **프로젝트 기간: 12/2 12:00 ~ 12/10 12:00

## 3. 요구 사항

### HTTP 메서드 POST를 이용해 Create, GET을 이용해 Read, PUT을 이용해 Update, DELETE를 이용해 Delete 기능을 구현해주세요.

- PUT : 해당하는 id에 해당하는 데이터를 갱신하는 기능을 구현합니다.
- DELETE : 해당하는 id에 해당하는 데이터를 삭제하는 기능을 구현합니다.
- POST: 요청이 들어올 때마다 id가 하나씩 증가하여 menu 리스트에 추가될 수 있도록 코드를 추가 구현합니다.
- SQL과 ORM 중 하나를 선택하여 데이터 베이스를 구현하여 제작합니다.
- 구현한 데이터베이스 연동을 구현합니다.

## 프로젝트 명세서

### Background

우리는 작은 로컬 카페 **Grids & Circles** 입니다. 고객들이 Coffe Bean package를 온라인 웹 사이트로 주문을 합니다. 매일 전날 오후 2시부터 오늘 오후 2시까지의 주문을 모아서 처리합니다.

현재는 총4개의 상품이 존재합니다.

우리는 별도의 회원을 관리하지 않습니다. email로 고객을 구분해요. 주문을 받을때 email을 같이 받아서 주문을 받습니다. 하나의 email로 하루에 여러번 주문을 받더라도 하나로 합쳐서 다음날 배송을 보내면 됩니다.

<aside>
💡

고객에게 “당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.”라고 알려 줍니다.

</aside>

###개발환경
* intelliJ

###백엔드 기술 스택
* Spring Boot
* dependencies
  * Spring Boot DevTools
  * Spring Web
  * Spring Data JDBC
  * Mybatis FrameWork
  * MariaDB

### 빌드도구
  * gradle
  * MariaDB

### 패키지 구조

### 테이블
![스크린샷 2024-12-08 시간: 15.17.17.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/757d63da-6797-4dbc-9b90-efd080a5c6af/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.17.17.png)

![스크린샷 2024-12-08 시간: 19.10.33.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/00c307c2-17e7-4044-b4f2-522039810c34/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_19.10.33.png)

### 쿼리
-- auto-generated definition
create schema gc_coffee collate utf8mb4_general_ci;

create table coffee
(
    coffee_id    int auto_increment
        primary key,
    coffee_name  varchar(50) not null,
    coffee_price int         not null
);

create table orders
(
    order_id    int auto_increment
        primary key,
    email       varchar(50) not null,
    total_price int         not null,
    order_date  datetime(6) not null,
    status      tinyint(1)  null,
    constraint orders_ibfk_1
        foreign key (email) references user (email)
            on update cascade on delete cascade
);
create table ordersdetail
(
    order_id  int not null,
    coffee_id int not null,
    quantity  int not null,
    primary key (order_id, coffee_id),
    constraint ordersdetail_ibfk_1
        foreign key (coffee_id) references coffee (coffee_id)
            on update cascade on delete cascade,
    constraint ordersdetail_ibfk_2
        foreign key (order_id) references orders (order_id)
            on update cascade on delete cascade
);

create table user
(
    email   varchar(50)  not null
        primary key,
    address varchar(200) not null,
    zipcode varchar(50)  not null
);

#1차 프로젝트 구현 내용
1. READ coffee 테이블
   ![스크린샷 2024-12-08 시간: 15.03.45.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/d4cbf39f-fd16-424c-94d6-fae8a2f3d195/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.03.45.png)
   
   ![스크린샷 2024-12-08 시간: 15.04.10.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/c7740e4c-e69e-4df8-ae57-b97418fe987d/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.04.10.png)


2. CREATE order 테이블 ( 주문 목록이 저장됨)
   ![스크린샷 2024-12-08 시간: 15.02.17.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/13800927-917c-4ebd-9d70-ab5ef52de55e/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.02.17.png)

   ![스크린샷 2024-12-08 시간: 15.03.10.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/ef61e0e5-3b55-4bfa-a04b-fac630f198fa/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.03.10.png)

3. UPDATE order 테이블 (배송전 = 0, 배송완료 1)
   ![스크린샷 2024-12-08 시간: 15.05.00.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/c916a002-8f85-4b52-b91f-58957ab25509/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.05.00.png)

   ![스크린샷 2024-12-08 시간: 15.05.08.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/acb07cc1-371a-4124-86a3-4f0379a32ddc/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.05.08.png)
   (배송 완료된 목록은 DB에 저장되어있지만 검색은 안됨)

4. DELETE 사용자 이메일에 맞는 데이터를 삭제
   ![스크린샷 2024-12-08 시간: 15.09.56.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/04307495-8143-4f7f-b488-fd2d17c96541/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.09.56.png)

   ![스크린샷 2024-12-08 시간: 15.10.02.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/89fde35f-d786-48b7-a620-56fc17eb00d5/1ca48566-c383-4a24-81f5-82e8437d8977/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-12-08_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A1%E1%86%AB_15.10.02.png)

   test@example.com 에 해당하는 주문 내역을 삭제 
