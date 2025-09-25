create table contracts (
    id bigserial primary key not null,
    version bigint,
    contract_date date not null,
    contract_number varchar(255) not null,
    contractor_id bigint not null references contractors,
    description text
);

insert into contracts (version ,contract_date, contract_number, contractor_id, description) values
(0, '2025-09-16', '1111111', 1, null),
(0, '2025-09-05', '1311111', 3, null),
(0, '2025-09-08', '1322222', 3, null),
(0, '2025-09-09', '1333333', 3, null),
(0, '2025-09-03', '1344444', 3, null),
(0, '2025-09-02', '1211111', 12, null),
(0, '2025-09-07', '1222222', 12, null),
(0, '2025-09-09', '1233333', 12, null),
(0, '2025-09-12', '1244444', 12, null);