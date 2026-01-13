create table users (
                       id uuid primary key,
                       email varchar(255) not null unique,
                       password varchar(255) not null,
                       created_at timestamp not null
);
