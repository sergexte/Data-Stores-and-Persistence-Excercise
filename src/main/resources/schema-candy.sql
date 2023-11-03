create table if not exists candy (
    id bigint not null,
    name nvarchar(255),
    price decimal(12,4),
    primary key (id)
);