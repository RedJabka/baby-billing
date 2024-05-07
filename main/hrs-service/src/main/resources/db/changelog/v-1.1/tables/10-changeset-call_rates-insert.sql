--liquibase formatted sql
--changeset Andrey Butusov:10
insert into call_rates
    (tariff_id, call_type, within_the_network_check, rate_per_minute)
values
    (11, '01', true, 1.5),
    (11, '01', false, 2.5),
    (11, '02', true, 0),
    (11, '02', false, 0);
--rollback delete from call_rates