insert into client
    (id, name)
values (1000, 'Client 1 name'),
       (2000, 'Client 2 name');

insert into client_address
    (id, client_id, street)
values (10, 1000, 'clent 1 street'),
       (11, 2000, 'clent 2 street');

insert into phone
    (client_id, number)
values (1000, '8 999 666 55 44'),
       (2000, '11 11 11'),
       (2000, '777 777 77 77');

