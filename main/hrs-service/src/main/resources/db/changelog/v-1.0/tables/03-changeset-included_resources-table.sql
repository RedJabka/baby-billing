--liquibase formatted sql
--changeset Andrey Butusov:3
create table
    included_resources (
                resource_id bigserial not null,
                tariff_id bigserial not null,
                resource_type varchar(255) not null,
                included_amount numeric(19, 2) not null,
                primary key (resource_id)
);
alter table included_resources
    add constraint fk_tariff_id foreign key (tariff_id) references tariffs(tariff_id);
--rollback alter table included_resources drop constraint fk_tariff_id;
--rollback drop table included_resources;