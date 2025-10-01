create table incoming_documents (
    id bigserial primary key not null,
    version bigint,
    incoming_document_date date not null,
    incoming_document_number varchar(255) not null,
    contract_id bigint not null references contracts,
    contractor_id bigint not null references contractors,
    warehouse_id bigint not null references warehouses,
    description text
);

insert into incoming_documents (version, incoming_document_date, incoming_document_number, contract_id, contractor_id, warehouse_id, description) values
(0, '2025-09-18', '1111111', 7, 12, 6, null),
(0, '2025-09-17', '2222222', 8, 12, 1, null),
(0, '2025-09-16', '3333333', 4, 3, 3, null);