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




