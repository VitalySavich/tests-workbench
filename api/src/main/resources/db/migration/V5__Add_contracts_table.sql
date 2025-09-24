create table contracts (
    id bigserial primary key not null,
    version bigint,
    contract_date date not null,
    contract_number varchar(255) not null,
    contractor_id bigint not null references contractors,
    description text
);

insert into contracts (version ,contract_date, contract_number, contractor_id, description) values
(0, '2025-09-16', '1134567', 1, null),
(0, '2025-09-08', '2765445', 3, null),
(0, '2025-09-02', '3246548', 12, null);