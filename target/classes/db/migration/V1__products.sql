CREATE TABLE products (
  id BIGINT AUTO_INCREMENT NOT NULL,
  code VARCHAR(150) NOT NULL UNIQUE,
  name VARCHAR(150) NOT NULL,
  address VARCHAR(150) NOT NULL,
  manufacturer VARCHAR(150),
  price BIGINT NOT NULL,
  status VARCHAR(50) NOT NULL,
  CONSTRAINT pk_products PRIMARY KEY(id)
) ENGINE=InnoDB character set = utf8 collate utf8_general_ci;

INSERT INTO products
(code, name, address, manufacturer, price, status)
VALUES
('liberty', 'Szabadság gumikacsa', 'szabadsag-gumikacsa', 'QuackQuack Inc.', 2500, 'ACTIVE'),
('professionsurgeon', 'Sebész gumikacsa', 'sebesz-gumikacsa', 'QuackQuack Inc.', 2400, 'ACTIVE'),
('animalbat', 'Denevér gumikacsa', 'denever-gumikacsa', 'QuackQuack Inc.', 2700, 'ACTIVE'),
('countrybigben', 'Big Ben gumikacsa', 'big-ben-gumikacsa', 'QuackQuack Inc.', 2500, 'DELETED'),
('premiumrobot', 'Robot gumikacsa', 'robot-gumikacsa', 'QuackQuack Inc.', 3000, 'ACTIVE'),
('alcapo', 'Al Capo gumikacsa', 'al-capo-gumikacsa', 'QuackQuack Inc.', 2200, 'ACTIVE'),
('darthvader', 'Darth Vader gumikacsa', 'darth-vader-gumikacsa', 'QuackQuack Inc.', 3000, 'ACTIVE'),
('dracula', 'Dracula gumikacsa', 'dracula-gumikacsa', 'QuackQuack Inc.', 2800, 'ACTIVE'),
('monster', 'Monster gumikacsa', 'monster-gumikacsa', 'QuackQuack Inc.', 3100, 'ACTIVE'),
('chef', 'Chef gumikacsa', 'chef-gumikacsa', 'QuackQuack Inc.', 2700, 'ACTIVE'),
('engineer', 'Mérnök gumikacsa', 'mernok-gumikacsa', 'QuackQuack Inc.', 3500, 'ACTIVE'),
('fireman', 'Tűzoltó gumikacsa', 'tuzolto-gumikacsa', 'QuackQuack Inc.', 3000, 'ACTIVE'),
('nurse', 'Ápolónő gumikacsa', 'apolono-gumikacsa', 'QuackQuack Inc.', 2900, 'ACTIVE'),
('police', 'Rendőr gumikacsa', 'rendor-gumikacsa', 'QuackQuack Inc.', 2500, 'ACTIVE'),
('easter', 'Húsvéti tojás gumikacsa', 'husveti-tojas-gumikacsa', 'QuackQuack Inc.', 2900, 'ACTIVE'),
('snowboard', 'Snowboard gumikacsa', 'snowboard-gumikacsa', 'QuackQuack Inc.', 3100, 'ACTIVE'),
('icecream', 'Jégkrém gumikacsa', 'jegkrem-gumikacsa', 'QuackQuack Inc.', 3500, 'ACTIVE'),
('fitness', 'Fitness gumikacsa', 'fitnes-gumikacsa', 'QuackQuack Inc.', 3900, 'ACTIVE'),
('football', 'Focista gumikacsa', 'focista-gumikacsa', 'QuackQuack Inc.', 3400, 'ACTIVE'),
('boxer', 'Boxoló gumikacsa', 'boxolo-gumikacsa', 'QuackQuack Inc.', 3600, 'ACTIVE');