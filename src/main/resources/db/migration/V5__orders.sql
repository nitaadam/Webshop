create table orders(
  id BIGINT AUTO_INCREMENT,
  user_id BIGINT,
  orderdate DATETIME,
  status VARCHAR(255),
  CONSTRAINT pk_orders PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
  ) ENGINE=InnoDB character set = utf8 collate utf8_general_ci;

  INSERT INTO orders (user_id, orderdate, status) VALUES
  (3, CURRENT_TIMESTAMP, 'ACTIVE'),
  (4, CURRENT_TIMESTAMP, 'ACTIVE'),
  (3, CURRENT_TIMESTAMP, 'DELIVERED'),
  (4, CURRENT_TIMESTAMP, 'DELIVERED'),
  (3, CURRENT_TIMESTAMP, 'DELETED'),
  (4, CURRENT_TIMESTAMP, 'DELETED');
