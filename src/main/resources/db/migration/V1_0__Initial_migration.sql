create table if not exists user_data
(
    id          uuid primary key,
    email       varchar(255) not null unique,
    phone       varchar(255) not null unique,
    password    varchar(65)  not null,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    age         int          not null,
    create_time timestamp    not null,
    update_time timestamp
);

create table if not exists category
(
    id          uuid primary key,
    title       varchar(255) not null,
    parent_id   uuid,
    create_time timestamp    not null,
    update_time timestamp,

    foreign key (parent_id) references category (id)
);

create table if not exists product
(
    id          uuid primary key,
    title       varchar(255)  not null,
    description text,
    price       numeric(6, 2) not null,
    create_time timestamp     not null,
    update_time timestamp,
    category_id uuid          not null,

    foreign key (category_id) references category (id)
);

create table if not exists cartDto
(
    id          uuid primary key,
    user_id     uuid          not null unique,
    total_cost  numeric(6, 2) not null,
    create_time timestamp     not null,
    update_time timestamp,

    foreign key (user_id) references user_data (id)
);

create table if not exists cart_product
(
    cart_id    uuid not null,
    product_id uuid not null,

    foreign key (cart_id) references cartDto (id),
    foreign key (product_id) references product (id)
);

create table if not exists user_favorite_product
(
    user_id    uuid not null,
    product_id uuid not null,

    foreign key (user_id) references user_data (id),
    foreign key (product_id) references product (id)
);