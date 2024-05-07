--liquibase formatted sql
--changeset Andrey Butusov:1
create table
    tariffs (
                tariff_id bigserial not null,
                tariff_name varchar(255) not null,
                billing_method varchar(255) not null,
                monthly_cost numeric(19, 2),
                description varchar(255),
                primary key (tariff_id)
);
--rollback drop table tariffs;