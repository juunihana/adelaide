create table category
(
    id        uuid primary key,
    name      varchar(255) not null,
    parent_id uuid,

    foreign key (parent_id) references category (id)
);

create table item
(
    id          uuid primary key,
    name        varchar(255)  not null,
    description text,
    price       numeric(6, 2) not null,
    category_id uuid          not null,

    foreign key (category_id) references category (id)
);
