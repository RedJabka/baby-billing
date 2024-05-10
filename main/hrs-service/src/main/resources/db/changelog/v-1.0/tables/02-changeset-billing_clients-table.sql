--liquibase formatted sql
--changeset Andrey Butusov:2
create table
    billing_clients (
                client_id bigserial not null,
                tariff_id bigserial not null,
                primary key (client_id)
);
alter table billing_clients
    add constraint fk_tariff_id foreign key (tariff_id) references tariffs(tariff_id);
--rollback alter table billing_clients drop constraint fk_tariff_id;
--rollback drop table billing_clients;
