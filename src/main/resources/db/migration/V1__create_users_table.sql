create table if not exists users(
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    age INT
);