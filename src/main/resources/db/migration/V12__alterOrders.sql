ALTER TABLE orders ADD delivery_address VARCHAR(255) DEFAULT 'no address';

INSERT INTO orders (user_id, orderdate, status, delivery_address) VALUES
(3, CURRENT_TIMESTAMP, 'ACTIVE', '3245 Heshan, Shopko Point út 152'),
(4, CURRENT_TIMESTAMP, 'ACTIVE', '3245 Heshan, Shopko Point út 152'),
(5, CURRENT_TIMESTAMP, 'DELIVERED', '3245 Heshan, Shopko Point út 152'),
(4, CURRENT_TIMESTAMP, 'DELIVERED', '7616 Novoukrainskiy, American Ash Crossing utca 67/b'),
(3, CURRENT_TIMESTAMP, 'DELETED', '7616 Novoukrainskiy, American Ash Crossing utca 67/b'),
(5, CURRENT_TIMESTAMP, 'DELETED', '7616 Novoukrainskiy, American Ash Crossing utca 67/b');

UPDATE orders SET delivery_address = '5047 Lupon, Meadow Vale Trail utca 80. fsz. 1' WHERE orders.id BETWEEN 1 AND 6;
