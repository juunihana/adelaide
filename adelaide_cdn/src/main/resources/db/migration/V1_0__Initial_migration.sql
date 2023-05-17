create table images (
    id uuid primary key not null,
    file_name varchar(255) unique not null,
    file_content bytea not null
);