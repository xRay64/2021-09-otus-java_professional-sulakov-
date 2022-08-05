create table client
(
    id           bigserial   not null primary key,
    name         varchar(50) not null
);

create table client_address
(
    id   bigserial   not null primary key,
    client_id bigint not null references client(id),
    street varchar(50) not null
);

create table phone
(
    client_id bigint      not null references client (id),
    number    varchar(50) not null
);
