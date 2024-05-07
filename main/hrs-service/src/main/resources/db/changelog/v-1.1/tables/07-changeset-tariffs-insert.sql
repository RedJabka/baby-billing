--liquibase formatted sql
--changeset Andrey Butusov:7
insert into tariffs
    (tarriff_id, tariff_name, billing_method, monthly_cost, description)
values
    (11, 'Classic', 'per minute', NULL, NULL),
    (12, 'Monthly', 'monthly', 100, 'from 51 minutes into "Classic"');

--rollback delete from tariffs;