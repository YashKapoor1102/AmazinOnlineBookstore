CREATE TABLE if not exists "user" (
    "isOwner" BOOLEAN NOT NULL,
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "password" VARCHAR(255) NOT NULL,
    "username" VARCHAR(255) NOT NULL
    );
