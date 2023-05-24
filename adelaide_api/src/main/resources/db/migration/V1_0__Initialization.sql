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
    avatar         varchar(100),
    bio            text,
    status         varchar(255),
    date_of_birth  timestamp           not null,
    place          varchar(255),
    time_joined    timestamp           not null,
    deleted        boolean
);

create table posts
(
    id           uuid      not null primary key,
    author_id    uuid      not null,
    title        varchar(255),
    content      text      not null,
    time_created timestamp not null,
    time_edited  timestamp,
    deleted      boolean,

    foreign key (author_id) references users (id)
);

create table comments
(
    id           uuid      not null primary key,
    author_id    uuid      not null,
    content      text      not null,
    time_created timestamp not null,

    foreign key (author_id) references users (id)
);

create table tags
(
    id              uuid               not null primary key,
    initial_post_id uuid               not null,
    name            varchar(50) unique not null,
    time_created    timestamp          not null,

    foreign key (initial_post_id) references posts (id)
);

create table votes
(
    id         uuid      not null primary key,
    user_id    uuid      not null,
    upvote     boolean   not null,
    time_voted timestamp not null,

    foreign key (user_id) references users (id)
);

create table groups
(
    id           uuid         not null primary key,
    name         varchar(100) not null,
    info         text,
    time_created timestamp    not null
);

create table users_password_history
(
    user_id       uuid                not null,
    password_hash varchar(255) unique not null,
    time_added    timestamp           not null,

    foreign key (user_id) references users (id)
);

create table users_images
(
    id      uuid         not null primary key,
    user_id uuid         not null,
    caption text,
    cdn_url varchar(255) not null,
    deleted boolean      not null,

    foreign key (user_id) references users (id)
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

create table users_friends
(
    user_id   uuid not null,
    friend_id uuid not null,

    foreign key (user_id) references users (id),
    foreign key (friend_id) references users (id)
);

create table users_groups
(
    user_id  uuid not null,
    group_id uuid not null,

    foreign key (user_id) references users (id),
    foreign key (group_id) references groups (id)
);

create table users_posts
(
    user_id uuid        not null,
    post_id uuid unique not null,

    foreign key (user_id) references users (id),
    foreign key (post_id) references posts (id)
);

create table groups_posts
(
    group_id uuid        not null,
    post_id  uuid unique not null,

    foreign key (group_id) references groups (id),
    foreign key (post_id) references posts (id)
);

create table posts_tags
(
    post_id uuid not null,
    tag_id  uuid not null,

    foreign key (post_id) references posts (id),
    foreign key (tag_id) references tags (id)
);

create table posts_votes
(
    post_id uuid not null,
    vote_id uuid not null,

    foreign key (post_id) references posts (id),
    foreign key (vote_id) references votes (id)
);

create table posts_comments
(
    post_id    uuid        not null,
    comment_id uuid unique not null,

    foreign key (post_id) references posts (id),
    foreign key (comment_id) references comments (id)
);

create table comments_votes
(
    comment_id uuid not null,
    vote_id    uuid not null,

    foreign key (comment_id) references comments (id),
    foreign key (vote_id) references votes (id)
);
