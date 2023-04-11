create table categories (
    id          bigserial primary key,
    title       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table products (
    id          bigserial primary key,
    title       varchar(255),
    price       numeric(8, 2) not null,
    category_id bigint references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into categories (title) values
('Продукты'),
('Бытовая химия');

insert into products (title, price, category_id) values
('Хлеб', 40.00, 1),
('Молоко', 80.00, 1),
('Масло', 120.00, 1),
('Сливки', 85.00, 1),
('Сметана', 80.00, 1),
('Кефир', 75.00, 1),
('Йогурт', 50.00, 1),
('Сыр', 130.00, 1),
('Творог', 75.00, 1),
('Колбаса', 200.00, 1),
('Сосиски', 150.00, 1),
('Рыба', 300.00, 1),
('Конфеты', 100.00, 1),
('Печенье', 50.00, 1),
('Варенье', 180.00, 1),
('Джем', 190.00, 1),
('Яблоки', 80.00, 1),
('Груши', 85.00, 1),
('Апельсины', 120.00, 1),
('Виноград', 100.00, 1),
('Порошок стиральный', 400.00, 2),
('Освежитель воздуха', 130.00, 2),
('Жидкое мыло', 150.00, 2),
('Отбеливатель', 180.00, 2),
('Пятновыводитель', 100.00, 2),
('Порошок для чистки', 92.00, 2);

create table orders
(
    id          bigserial primary key,
    username        varchar(255),
    total_price numeric(8, 2) not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);


create table order_items
(
    id                bigserial primary key,
    order_id          bigint not null references orders (id),
    product_id        bigint not null references products (id),
    price_per_product numeric(8, 2)    not null,
    quantity          int    not null,
    price             numeric(8, 2)    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);



create table users (
  id                    bigserial primary key,
  username              varchar(36) not null unique,
  password              varchar(80) not null,
  firstname             varchar(80),
  lastname              varchar(80),
  email                 varchar(50) unique,
  created_at            timestamp default current_timestamp,
  updated_at            timestamp default current_timestamp
);

create table roles (
  id                    serial primary key,
  name                  varchar(50) not null,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
  user_id               bigint not null,
  role_id               bigint not null,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name) values
('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN'), ('ROLE_SUPERADMIN');

insert into users (username, password, firstname, lastname, email) values
('user', '$2a$12$TK5JzECdm04yRJ1Gtg5RW.ptVT1FMCqLywGBsbw97AEJHxRGwPJXK', 'Иван', 'Сергеев', 'user@gmail.com'),
('manager', '$2a$12$TK5JzECdm04yRJ1Gtg5RW.ptVT1FMCqLywGBsbw97AEJHxRGwPJXK', 'Петр', 'Александров', 'manager@gmail.com'),
('admin', '$2a$12$TK5JzECdm04yRJ1Gtg5RW.ptVT1FMCqLywGBsbw97AEJHxRGwPJXK', 'Александр', 'Иванов', 'admin@gmail.com'),
('superadmin', '$2a$12$TK5JzECdm04yRJ1Gtg5RW.ptVT1FMCqLywGBsbw97AEJHxRGwPJXK', 'Сергей', 'Петров', 'superadmin@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3),
(4, 4);
