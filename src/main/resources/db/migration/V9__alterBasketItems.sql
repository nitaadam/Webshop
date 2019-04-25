ALTER TABLE basket_items
ADD COLUMN quantity INT DEFAULT 1 AFTER product_id ;

INSERT INTO basket_items (id, basket_id, product_id, quantity) VALUES (112, 33, 1, 1), (122, 33, 2, 2);
INSERT INTO basket_items (id, basket_id, product_id, quantity) VALUES (152, 44, 2, 2), (162, 44, 4, 1);