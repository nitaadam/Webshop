CREATE TABLE baskets(
id BIGINT AUTO_INCREMENT,
user_id BIGINT,
CONSTRAINT pk_baskets PRIMARY KEY(id),
FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB character set = utf8 collate utf8_general_ci;

INSERT INTO baskets (id, user_id) VALUES (33, 3), (44, 4);


