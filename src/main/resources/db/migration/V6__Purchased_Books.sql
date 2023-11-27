CREATE TABLE "purchasedbooks" (
                                  user_id BIGINT,
                                  purchasedbooks_id BIGINT,
                                  PRIMARY KEY (user_id, purchasedbooks_id),
                                  FOREIGN KEY (user_id) REFERENCES "user" ("id"),
                                  FOREIGN KEY (purchasedbooks_id) REFERENCES "book" ("id")
);
