-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 11 increment by 1;

create table client
(
    id           bigint not null primary key,
    name         varchar(255),
    password_md5 varchar(255)
);

create table address
(
    id        bigint not null primary key,
    street    varchar(255),
    client_id bigint
        constraint fk_client_address
            references client
);

create table phone
(
    id        bigint not null primary key,
    number    varchar(255),
    client_id bigint
        constraint fk_client_phone
            references client
);

insert into client
    (id, name, password_md5)
values (1, 'user1', md5('111')),
       (2, 'user2', md5('111')),
       (3, 'user3', md5('111'));

insert into address
    (id, street, client_id)
values
    (4, 'user1 address', 1),
    (5, 'user2 address', 2),
    (6, 'user3 address', 3);

insert into phone
    (id, number, client_id)
values
    (7, '11 11 11', 1),
    (8, '+1 (111) 111 11 11', 1),
    (9, '22 22 22', 2),
    (10, '+3 (333) 333 33 33', 3);
