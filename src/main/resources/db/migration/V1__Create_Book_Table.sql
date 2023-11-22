CREATE TABLE if not exists "book" (
        "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
        "author" VARCHAR(255) NOT NULL,
        "description" VARCHAR(1000) NOT NULL,
        "isbn" VARCHAR(255) NOT NULL,
        "publisher" VARCHAR(255) NOT NULL,
        "title" VARCHAR(255) NOT NULL

);
