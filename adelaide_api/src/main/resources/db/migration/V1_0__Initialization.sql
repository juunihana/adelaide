create table users
(
    id             uuid                not null primary key,
    username       varchar(100) unique not null,
    email          varchar(100) unique not null,
    password_hash  varchar(255)        not null,
    phone          varchar(15) unique,
    first_name     varchar(100)        not null,
    middle_name    varchar(100),
    last_name      varchar(100)        not null,
    maiden_surname varchar(100),
    bio            text,
    status         varchar(255),
    date_of_birth  timestamp           not null,
    place          varchar(255),
    time_joined    timestamp           not null
);

create table users_password_history
(
    id            uuid         not null primary key,
    user_id       uuid         not null,
    password_hash varchar(255) not null,
    time_added    timestamp    not null,

    foreign key (user_id) references users_info (id)
);

create table users_images
(
    id      uuid                not null primary key,
    user_id uuid                not null,
    caption text,
    cdn_url varchar(255) unique not null,
    deleted boolean             not null,

    foreign key (user_id) references users_info (id)
);

create table posts
(
    id           uuid      not null primary key,
    user_id      uuid      not null,
    author_id    uuid      not null,
    title        varchar(255),
    content      text      not null,
    time_created timestamp not null,
    time_edited  timestamp,
    deleted      boolean,

    foreign key (user_id) references users_info (id),
    foreign key (author_id) references users_info (id)
);


