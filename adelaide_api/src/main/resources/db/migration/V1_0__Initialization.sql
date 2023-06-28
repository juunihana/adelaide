create table users
(
    id            uuid                not null primary key,
    username      varchar(100) unique not null,
    email         varchar(100) unique not null,
    password_hash varchar(255)        not null,
    display_name  varchar(100)        not null,
    avatar        varchar(100),
    bio           text,
    date_of_birth timestamp           not null,
    place         varchar(255),
    time_joined   timestamp           not null
);

create table users_password_history
(
    user_id       uuid                not null,
    password_hash varchar(255) unique not null,
    time_added    timestamp           not null,

    foreign key (user_id) references users (id)
);

create table communities
(
    id           uuid         not null primary key,
    name         varchar(100) not null,
    info         text,
    time_created timestamp    not null
);

create table posts
(
    id           uuid      not null primary key,
    author_id    uuid      not null,
    community_id uuid      not null,
    reply_to_id  uuid,
    title        varchar(255),
    content      text      not null,
    rating       bigint,
    time_created timestamp not null,
    time_edited  timestamp,
    deleted      boolean,

    foreign key (author_id) references users (id),
    foreign key (community_id) references communities (id),
    foreign key (reply_to_id) references posts (id)
);

create table posts_images
(
    id      uuid         not null primary key,
    post_id uuid         not null,
    cdn_url varchar(100) not null,
    foreign key (post_id) references posts (id)
);

create table votes
(
    id         uuid      not null primary key,
    user_id    uuid      not null,
    post_id    uuid      not null,
    upvote     boolean   not null,
    time_voted timestamp not null,

    foreign key (user_id) references users (id),
    foreign key (post_id) references posts (id)
);

create table users_friends
(
    user_id   uuid not null,
    friend_id uuid not null,

    foreign key (user_id) references users (id),
    foreign key (friend_id) references users (id)
);

create table users_friends_requests
(
    user_id       uuid      not null,
    friend_id     uuid      not null,
    accepted      boolean,
    time_created  timestamp not null,
    time_resolved timestamp,

    foreign key (user_id) references users (id),
    foreign key (friend_id) references users (id)
);

create table users_communities
(
    user_id      uuid not null,
    community_id uuid not null,
    accepted     boolean,

    foreign key (user_id) references users (id),
    foreign key (community_id) references communities (id)
);
