create table warehouse_items (
    id bigserial primary key not null,
    version bigint,
    price numeric(12,2) not null,
    quantity numeric(12,2) not null,
    amount numeric(12,2) not null,
    warehouse_item_type_id bigint not null references warehouse_item_types
);

insert into warehouse_items (version, quantity, price, amount, warehouse_item_type_id) values
(0, 20.00, 10.05, 200.00, 10),
(0, 25.00, 15.00, 375.00, 1),
(0, 10.00, 17.00, 170.00, 21),
(0, 15.00, 15.00, 225.00, 19),
(0, 20.00, 20.00, 400.00, 12);