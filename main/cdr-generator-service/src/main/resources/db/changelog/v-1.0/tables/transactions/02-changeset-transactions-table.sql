--liquibase formatted sql
--changeset Andrey Butusov:2
create table
    transactions (
                transaction_id bigserial not null,
                call_type varchar(2) not null,
                subscriber_served_id bigint not null,
                subscriber_connected_id bigint not null,
                start_time timestamp not null,
                end_time timestamp not null,
                primary key (transaction_id)
);
alter table transactions
    add constraint fk_subscriber_served_id foreign key (subscriber_served_id)
        references subscribers(subscriber_id);
alter table transactions
    add constraint fk_subscriber_connected_id foreign key (subscriber_connected_id)
        references subscribers(subscriber_id);
--rollback alter table transactions drop constraint fk_subscriber_connected_id;
--rollback alter table transactions drop constraint fk_subscriber_served_id;
--rollback drop table transactions;