drop table if exists items;

create table if not exists items
(
    id          serial primary key not null,
    created     date,
    description varchar(2000),
    done        varchar(10)
);