create table tasks (
    id uuid primary key,
    title varchar(255),
    description text,
    time_created timestamp not null
);