--liquibase formatted sql
--changeset Andrey Butusov:1
create table
    romashka_clients (
                client_id bigserial not null,
                msisdn varchar(20) not null,
                tariff_id bigserial not null,
                balance numeric(19, 2) not null,
                primary key (client_id)
);
--rollback drop table romashka_clients;