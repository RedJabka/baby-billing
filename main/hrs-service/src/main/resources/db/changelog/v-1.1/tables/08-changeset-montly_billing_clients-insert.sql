--liquibase formatted sql
--changeset Andrey Butusov:8
-- insert into montly_billing_clients
--     (client_id, tariff_id)
-- values
--     (1, 12),
--     (2, 12);
--rollback delete from montly_billing_clients