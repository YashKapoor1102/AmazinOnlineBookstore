-- V1__create_book_table.sql

CREATE TABLE if not exists Book (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      isbn VARCHAR(255) NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      publisher VARCHAR(255) NOT NULL,
                      description VARCHAR(1000) NOT NULL
);
