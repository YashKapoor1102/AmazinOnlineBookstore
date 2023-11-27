CREATE TABLE "recommendations" (
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "user_id" BIGINT NOT NULL,
    "book_id" BIGINT NOT NULL,
    "weight" INT NOT NULL,
    FOREIGN KEY ("user_id") REFERENCES "user" ("id"),
    FOREIGN KEY ("book_id") REFERENCES "book" ("id")
);