create table category
(
    id          uuid primary key,
    name        varchar(255) not null,
    parent_id   uuid,
    create_time timestamp    not null,

    foreign key (parent_id) references category (id)
);

create table product
(
    id          uuid primary key,
    name        varchar(255)  not null,
    description text,
    price       numeric(6, 2) not null,
    create_time timestamp     not null,
    category_id uuid          not null,

    foreign key (category_id) references category (id)
);

create table cart
(
    id          uuid primary key,
    sum         numeric(6, 2) not null,
    create_time timestamp     not null
);

create table cart_product
(
    cart_id    uuid not null,
    product_id uuid not null,

    foreign key (cart_id) references cart (id),
    foreign key (product_id) references product (id)
)