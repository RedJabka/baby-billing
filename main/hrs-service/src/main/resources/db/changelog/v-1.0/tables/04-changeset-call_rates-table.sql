--liquibase formatted sql
--changeset Andrey Butusov:4
create table
    call_rates (
                call_rate_id bigserial not null,
                tariff_id bigserial not null,
                call_type varchar(2) not null,
                within_the_network_check boolean not null,
                rate_per_minute numeric(19, 2) not null,
                primary key (call_rate_id)
);
alter table call_rates
    add constraint fk_tariff_id foreign key (tariff_id) references tariffs(tariff_id);
--rollback alter table call_rates drop constraint fk_tariff_id;
--rollback drop table call_rates;