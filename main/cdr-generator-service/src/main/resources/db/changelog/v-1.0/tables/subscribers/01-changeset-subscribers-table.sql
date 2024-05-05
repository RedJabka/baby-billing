--liquibase formatted sql
--changeset Andrey Butusov:1
create table
    subscribers (
                subscriber_id bigserial not null,
                msisdn varchar(20) not null,
                primary key (subscriber_id)
);
--rollback drop table subscribers;