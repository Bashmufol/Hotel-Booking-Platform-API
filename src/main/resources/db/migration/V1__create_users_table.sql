create table if not exists users(
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    age INT,
    role VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL
);