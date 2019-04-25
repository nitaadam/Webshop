create table basket_items(
  id BIGINT AUTO_INCREMENT,
  basket_id BIGINT,
  product_id BIGINT,
  CONSTRAINT pk_basket_items PRIMARY KEY(id),
  FOREIGN KEY (basket_id) REFERENCES baskets(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
  ) ENGINE=InnoDB character set = utf8 collate utf8_general_ci;




