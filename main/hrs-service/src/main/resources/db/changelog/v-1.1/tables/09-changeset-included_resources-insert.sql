--liquibase formatted sql
--changeset Andrey Butusov:9
insert into included_resources
    (tariff_id, resource_type, included_amount)
values
    (12, 'minute', 50);
--rollback delete from included_resources;