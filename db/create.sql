drop table if exists items;

create table if not exists items
(
    id          serial primary key not null,
    created     date,
    description varchar(2000),
    done        varchar(10)
);
drop table if exists j_role;

create table if not exists j_role
(
    id   serial primary key,
    name varchar(2000)
);
drop table if exists j_user;

create table if not exists j_user
(
    id      serial primary key,
    name    varchar(2000),
    role_id int not null references j_role (id)
);