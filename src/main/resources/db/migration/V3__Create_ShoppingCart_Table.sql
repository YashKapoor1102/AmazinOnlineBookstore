CREATE TABLE if not exists "ShoppingCart" (
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "user_id" BIGINT NOT NULL,
    CONSTRAINT fk_user
    FOREIGN KEY ("user_id")
    REFERENCES "user" ("id")
);

