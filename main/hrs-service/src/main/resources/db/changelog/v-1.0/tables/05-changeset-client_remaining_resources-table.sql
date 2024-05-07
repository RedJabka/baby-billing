--liquibase formatted sql
--changeset Andrey Butusov:5
create table
    client_remaining_resources (
                remaining_resources_id bigserial not null,
                client_id bigserial not null,
                resource_id bigserial not null,
                remaining_resource_amount numeric(19, 2) not null,
                primary key (remaining_resources_id)
);
alter table client_remaining_resources
    add constraint fk_client_id foreign key (client_id) references monthly_billing_clients(client_id);
alter table client_remaining_resources
    add constraint fk_resource_id foreign key (resource_id) references included_resources(resource_id);
--rollback alter table client_remaining_resources drop constraint fk_client_id;
--rollback alter table client_remaining_resources drop constraint fk_resource_id;
--rollback drop table client_remaining_resources;