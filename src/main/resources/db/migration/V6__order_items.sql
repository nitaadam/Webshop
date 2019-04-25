create table order_items(
  id BIGINT AUTO_INCREMENT,
  order_id BIGINT,
  product_id BIGINT,
  price BIGINT NOT NULL,
  CONSTRAINT pk_order_items PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
  ) ENGINE=InnoDB character set = utf8 collate utf8_general_ci;

  INSERT INTO order_items (order_id, product_id, price) VALUES
  (1, 1, 2500),
  (1, 2, 2400),
  (1, 5, 3000),
  (2, 2, 2400),
  (2, 4, 2500),
  (3, 5, 3000),
  (3, 1, 2500),
  (4, 5, 3000),
  (4, 3, 2700),
  (5, 2, 2400),
  (5, 4, 2500),
  (6, 1, 2500),
  (6, 3, 2700);

