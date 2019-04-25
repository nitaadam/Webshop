DELETE from order_items;
DELETE from orders;
DELETE from basket_items;
DELETE from baskets;
DELETE from feedbacks;
DELETE from products;
DELETE from categories;
DELETE from users;

INSERT INTO categories(id, name, view_order) values
(1, 'other', 1),
(2, 'normal', 2),
(3, 'nature', 3),
(4, 'sport', 4),
(5, 'sense', 5),
(6, 'none', 6);

INSERT INTO products(id, code, name, address, manufacturer, price, status, category_id) values
(1, 'fairytaleminion', 'Minion gumikacsa', 'minion-gumikacsa', 'QuackQuack Inc.', 2500, 'ACTIVE', 1),
(2, 'professionsurgeon', 'Sebész gumikacsa', 'sebesz-gumikacsa', 'QuackQuack Inc.', 2400, 'ACTIVE', 2),
(3, 'animalbat', 'Denevér gumikacsa', 'denever-gumikacsa', 'QuackQuack Inc.', 2700, 'ACTIVE', 2),
(4, 'countrybigben', 'Big Ben gumikacsa', 'big-ben-gumikacsa', 'QuackQuack Inc.', 2500, 'DELETED', 1),
(5, 'premiumrobot', 'Robot gumikacsa', 'robot-gumikacsa', 'QuackQuack Inc.', 3000, 'ACTIVE', 1);

INSERT INTO users (id, name, username, password, role) values
(1, 'user', 'user', 'userName', 'ROLE_USER'),
(2, 'admin', 'admin', 'admin', 'ROLE_ADMIN'),
(3, 'Admin Ármin', 'admin1', 'admin1', 'ROLE_ADMIN'),
(4, 'User Ferenc', 'user1', 'user1', 'ROLE_USER');

INSERT INTO baskets (id, user_id) values
(11, 1), (22, 2), (33, 3);

INSERT INTO basket_items (id, basket_id, product_id, quantity) values
(112, 11, 1, 1), (122, 11, 2, 2),
(152, 22, 2, 2), (162, 22, 3, 1);

INSERT INTO orders (id, user_id, orderdate, status) values
(1, 1, '2019-03-25 12:00', 'ACTIVE'),
(2, 1, '2019-03-26 15:05', 'DELIVERED'),
(3, 4, '2019-03-24 13:56', 'DELETED'),
(4, 4, '2019-03-25 14:24', 'ACTIVE');

INSERT INTO order_items (order_id, product_id, price, id) values
(1, 1, 2018, 2),
(1, 2, 3114, 3),
(2, 2, 2019, 1),
(2, 1, 2019, 4),
(2, 3, 2020, 5),
(3, 1, 2018, 6),
(3, 2, 2019, 7);