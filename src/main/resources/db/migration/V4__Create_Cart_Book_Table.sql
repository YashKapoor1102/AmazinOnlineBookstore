CREATE TABLE if not exists "Cart_Book" (
    "cart_id" BIGINT NOT NULL,
    "book_id" BIGINT NOT NULL,
    PRIMARY KEY ("cart_id", "book_id"),
    CONSTRAINT fk_cart
    FOREIGN KEY ("cart_id")
    REFERENCES "ShoppingCart" ("id"),
    CONSTRAINT fk_book
    FOREIGN KEY ("book_id")
    REFERENCES "book" ("id")
);
