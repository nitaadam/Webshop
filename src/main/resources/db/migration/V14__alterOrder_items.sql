ALTER TABLE order_items
ADD COLUMN quantity INT DEFAULT 1 AFTER product_id ;

INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (29, 1, 12, 2, 3000), (30, 1, 16, 3, 3100), (31, 1, 19, 2, 3400);
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (14, 7, 2, 1, 2400), (15, 7, 8, 2, 2800);
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (17, 11, 6, 1, 2200), (18, 11, 10, 3, 2700), (19, 11, 14, 2, 2500);
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (20, 8, 9, 1, 3100), (21, 8, 13, 3, 2900);
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (22, 10, 8, 1, 2800), (23, 10, 15, 3, 2900);
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (24, 9, 5, 1, 3000), (25, 9, 11, 2, 3500), (26, 9, 12, 2, 3000);
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES (27, 12, 9, 2, 3100), (28, 12, 20, 3, 3600);
