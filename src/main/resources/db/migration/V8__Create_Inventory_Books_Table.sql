create table "inventory_books"
(
    "id" BIGINT NOT NULL,
    "stock"   BIGINT NOT NULL,
    "book_id" BIGINT  not null,
    primary key ("id", "book_id"),
    CONSTRAINT fk_inventory_id
    FOREIGN KEY ("id")
    REFERENCES "inventory" ("id"),
    CONSTRAINT fk_inventory_book
    FOREIGN KEY ("book_id")
    REFERENCES "book" ("id")
);

