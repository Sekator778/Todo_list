-- drop table if exists items;
create table if not exists items
(
    id   serial primary key not null,
    description varchar(2000)
);