create table incoming_document_warehouse_item (
    incoming_document_id bigint not null references incoming_documents,
    warehouse_item_id bigint not null references warehouse_items
);

insert into incoming_document_warehouse_item (incoming_document_id, warehouse_item_id) values
(2, 2),
(2, 3),
(2, 5),
(3, 3),
(3, 4);