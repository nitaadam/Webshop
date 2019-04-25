ALTER TABLE products
ADD COLUMN category_id BIGINT DEFAULT 1;

ALTER TABLE products
ADD CONSTRAINT fk_products_categories
FOREIGN KEY(category_id) REFERENCES categories(id);

UPDATE products SET category_id = 1 WHERE products.code = 'professionsurgeon';
UPDATE products SET category_id = 1 WHERE products.code = 'engineer';
UPDATE products SET category_id = 1 WHERE products.code = 'fireman';
UPDATE products SET category_id = 1 WHERE products.code = 'nurse';
UPDATE products SET category_id = 1 WHERE products.code = 'chef';
UPDATE products SET category_id = 1 WHERE products.code = 'police';
UPDATE products SET category_id = 1 WHERE products.code = 'nurse';
UPDATE products SET category_id = 2 WHERE products.code = 'easter';
UPDATE products SET category_id = 2 WHERE products.code = 'icecream';
UPDATE products SET category_id = 2 WHERE products.code = 'snowboard';
UPDATE products SET category_id = 4 WHERE products.code = 'fitness';
UPDATE products SET category_id = 4 WHERE products.code = 'football';
UPDATE products SET category_id = 4 WHERE products.code = 'boxer';
UPDATE products SET category_id = 5 WHERE products.code = 'darthvader';
UPDATE products SET category_id = 5 WHERE products.code = 'dracula';
UPDATE products SET category_id = 5 WHERE products.code = 'monster';
UPDATE products SET category_id = 6 WHERE products.code = 'liberty';
UPDATE products SET category_id = 5 WHERE products.code = 'animalbat';
UPDATE products SET category_id = 6 WHERE products.code = 'premiumrobot';
UPDATE products SET category_id = 5 WHERE products.code = 'alcapo';